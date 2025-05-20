            Mono<String> monoRawJson = mobinsWebClient.post()
                    .uri(petApiPath)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(ReqCommonBody.of(reqCommonStat.getBasDt())), ReqCommonBody.class)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, response -> {
                        return response.bodyToMono(String.class).map(RuntimeException::new);
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, response -> {
                        return response.bodyToMono(String.class).map(RuntimeException::new);
                    })
                    .onStatus(HttpStatusCode::isError, response -> {
                        return response.bodyToMono(String.class).map(RuntimeException::new);
                    })
                    .bodyToMono(String.class)
                    .log()
                    ;
