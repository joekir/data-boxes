package main

import (
	"context"
	"fmt"
	"log"
	"net"

	pb "github.com/joekir/data-boxes/proto/authorizer"
	"google.golang.org/grpc"
	// 	"google.golang.org/protobuf/types/known/wrapperspb"
)

const (
	PORT = 9000
)

type server struct{}

func (h *server) IsAuthorized(context context.Context, authRequest *pb.AuthRequest) (*pb.AuthResponse, error) {
	log.Printf("IsAuthorized called: %v\n", authRequest)
	return &pb.AuthResponse{Result: "foo"}, nil
}

func main() {
	fmt.Printf("Starting Authorizer Service at localhost:%d\n", PORT)

	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", PORT))
	if err != nil {
		log.Fatalf("failed to listen: %v\n", err)
	}

	s := server{}
	grpcServer := grpc.NewServer()

	pb.RegisterAuthorizerServer(grpcServer, &s)

	if err := grpcServer.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %s\n", err)
	}
}
