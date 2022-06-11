//go:generate protoc -I/usr/local/include -I. -I$GOPATH/src --go_opt=Mauthorizer.proto --go_out=plugins=grpc:. authorizer.proto

package servicepb
