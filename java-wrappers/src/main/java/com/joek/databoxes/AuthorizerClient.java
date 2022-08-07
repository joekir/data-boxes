package com.joek.databoxes;

import com.joekir.databoxes.authorizer.AuthRequest;
import com.joekir.databoxes.authorizer.AuthResponse;
import com.joekir.databoxes.authorizer.AuthorizerGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthorizerClient {
    private static final Logger logger = Logger.getLogger(AuthorizerClient.class.getName());

    private final AuthorizerGrpc.AuthorizerBlockingStub blockingStub;

    public AuthorizerClient(Channel channel) {
        // 'channel' here is a Channel, not a ManagedChannel, so it is not this code's responsibility to
        // shut it down.

        // Passing Channels to code makes code easier to test and makes it easier to reuse Channels.
        blockingStub = AuthorizerGrpc.newBlockingStub(channel);
    }

    public void authorize() {
        AuthRequest request = AuthRequest.newBuilder().setAuthContext("foo").setVerb("READ").setDataType("String").build();
        AuthResponse response;
        try {
            response = blockingStub.isAuthorized(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
        }
    }
}
