package org.tron.sunserver;

import io.grpc.*;

/**
 * @author TRON
 * @description: TODO
 * @date 2025/5/19
 */
public class HeaderClientInterceptor implements ClientInterceptor {
  private final Metadata headers;

  public HeaderClientInterceptor(Metadata headers) {
    this.headers = headers;
  }

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        next.newCall(method, callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata metadata) {
        metadata.merge(headers);
        super.start(responseListener, metadata);
      }
    };
  }

}