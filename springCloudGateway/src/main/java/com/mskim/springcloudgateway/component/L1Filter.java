package com.mskim.springcloudgateway.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class L1Filter extends AbstractGatewayFilterFactory<L1Filter.Config> {

    public L1Filter() {

        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            if (config.isPre()) {
                // 지역필터 마이크로 서비스를 거치기 전
                System.out.println("requestUrl : [Pre][LocalFilter1] " + exchange.getAttributes().get("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayPredicatePathContainer"));
            }

            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {

                        if (config.isPost()) {
                            // 지역필터 마이크로 서비스를 거치고 난 후
                            System.out.println("requestUrl : [Post][LocalFilter1] " + exchange.getAttributes().get("org.springframework.cloud.gateway.support.ServerWebExchangeUtils.gatewayPredicatePathContainer"));
                        }
                    }));
        };
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Config {
        // pre 값이 트루인 경우
        private boolean pre;
        // post 값이 트루인 경우
        private boolean post;
    }

}
