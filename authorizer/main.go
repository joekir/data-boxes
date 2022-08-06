package main

import (
	"context"
	"log"
	"net"
	"os"
	"os/signal"
	"path"
	"syscall"

	pb "github.com/joekir/data-boxes/proto/authorizer"
	"google.golang.org/grpc"
	"google.golang.org/protobuf/types/known/wrapperspb"
)

const (
	sockname = "authorizer.sock"
)

type server struct{}

func (h *server) IsAuthorized(context context.Context, authRequest *pb.AuthRequest) (*wrapperspb.BoolValue, error) {
	log.Printf("IsAuthorized called: %#v\n", authRequest)
	return &wrapperspb.BoolValue{Value: true}, nil
}

func cleanup(socketpath string) {
	log.Printf("Unlinking sock file: %s", socketpath)
	syscall.Unlink(socketpath)
}

func main() {
	socketpath := path.Join("/tmp", sockname)

	// Remove previously existing sock file
	syscall.Unlink(socketpath)
	c := make(chan os.Signal)
	signal.Notify(c, os.Interrupt, syscall.SIGTERM)
	go func() {
		<-c
		cleanup(socketpath)
		os.Exit(1)
	}()

	log.Printf("Linking sock file: %s", socketpath)
	lis, err := net.Listen("unix", socketpath)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	s := grpc.NewServer()
	pb.RegisterAuthorizerServer(s, &server{})
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
