package main

import (
	"context"
	"fmt"
	"log"
	"net"

	pb "github.com/joekir/data-boxes/proto/authorizer"
	"google.golang.org/grpc"
	// 	"google.golang.org/protobuf/types/known/wrapperspb"
	"github.com/open-policy-agent/opa/rego"
)

const (
	PORT = 9000
)

type server struct{}

func (h *server) IsAuthorized(context context.Context, authRequest *pb.AuthRequest) (*pb.AuthResponse, error) {
	log.Printf("IsAuthorized called: %v\n", authRequest)
    // https://www.openpolicyagent.org/docs/latest/integration/#integrating-with-the-go-api
    const config = "policies/rbac.rego"
	_, err := rego.New(
        rego.Query("x = data.example.authz.allow"),
        rego.Module("example.rego", config),
        ).PrepareForEval(context)

    if err != nil {
        log.Fatal(err)
    }
	return &pb.AuthResponse{Result: "foo"}, nil
}

func main() {
	fmt.Printf("Starting Authorizer Service at localhost:%d\n", PORT)

	lis, err := net.Listen("tcp", fmt.Sprintf(":%d", PORT))
	if err != nil {
		log.Fatal("failed to listen: %v\n", err)
	}

	s := server{}
	grpcServer := grpc.NewServer()

	pb.RegisterAuthorizerServer(grpcServer, &s)

	if err := grpcServer.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %s\n", err)
	}
}
