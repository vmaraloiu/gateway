/**
 * Copyright 2007-2015, Kaazing Corporation. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.kaazing.gateway.service.http.directory.cachecontrol;

import static org.junit.rules.RuleChain.outerRule;

import java.io.File;
import java.net.URI;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.kaazing.gateway.server.test.GatewayRule;
import org.kaazing.gateway.server.test.config.GatewayConfiguration;
import org.kaazing.gateway.server.test.config.builder.GatewayConfigurationBuilder;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;

/**
 * RFC-7234, section 5.2.1 Request Cache-Control Directives
 */
public class CacheControlInRequestIT {
    private final K3poRule k3po = new K3poRule().setScriptRoot("org/kaazing/specification/http/rfc7234/request.cache-control");

    private final GatewayRule gateway = new GatewayRule() {
        {
            GatewayConfiguration configuration =
                    new GatewayConfigurationBuilder()
                        .webRootDirectory(new File("src/test/webapp"))
                        .service()
                            .accept(URI.create("http://localhost:8000/"))
                            .type("directory")
                            .property("directory", "/public")
                            .property("welcome-file", "index.html")
                            .nestedProperty("location")
                                .property("patterns", "**/*")
                                .property("cache-control", "max-age=100")
                            .done()
                        .done()
                    .done();
            init(configuration);
        }
    };

    @Rule
    public final TestRule chain = outerRule(k3po).around(gateway);

    @Test
    @Specification({"max-age.fresh.response.from.cache/request"})
    public void shouldReceiveCachedResponseWithMaxAgeWhenCachedResponseIsFresh() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"max-age.stale.response.conditional.request.304/request"})
    public void shouldReceiveNotModifiedWhenCachedResponseIsStaleForConditionalRequestWithMaxAge() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"max-age.stale.response.unconditional.request.200/request"})
    public void shouldReceiveNotModifiedWhenCachedResponseIsStaleForUnconditionalRequestWithMaxAge() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"max-stale.any.age.from.cache/request"})
    public void shouldReceiveCachedResponseForMaxStaleWithNoValue() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"max-stale.stale.response.from.cache/request"})
    public void shouldReceiveCachedResponseForMaxStaleWithinLimit() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"max-stale.stale.response.conditional.request.304/request"})
    public void shouldReceiveNotModifiedWithStaleCachedResponseWhenMaxStaleExceedsLimitForConditionalRequest() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"max-stale.stale.response.unconditional.request.200/request"})
    public void shouldReceiveOKWithStaleCachedResponseWhenMaxStaleExceedsLimitForUnconditionalRequest() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"min-fresh.fresh.response.from.cache/request"})
    public void shouldReceiveCachedResponseForMinFreshWithinLimit() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"min-fresh.fresh.response.conditional.request.304/request"})
    public void shouldReceiveNotModifiedWithFreshCachedResponseWhenMinFreshExceedsLimitForForConditionalRequest()
            throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"min-fresh.fresh.response.unconditional.request.200/request"})
    public void shouldReceiveOKWithFreshCachedResponseWhenMinFreshExceedsLimitForForUnconditionalRequest() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"min-fresh.stale.response.conditional.request.304/request"})
    public void shouldReceiveNotModifiedWithStaleCachedResponseWhenMinFreshExceedsLimitForConditionalRequest() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"min-fresh.stale.response.unconditional.request.200/request"})
    public void shouldReceiveNotModifiedWithStaleCachedResponseWhenMinFreshExceedsLimitForUnconditionalRequest()
            throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"no-cache.conditional.request.304/request"})
    public void shouldReceiveNotModifiedWithNoCacheForConditionalRequest() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"no-cache.unconditional.request.200/request"})
    public void shouldReceiveOKWithNoCacheForUnconditionalRequest() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"no-transform/request"})
    public void shouldReceiveUntransformedCachedResponse() throws Exception {
        k3po.finish();
    }

    @Test
    @Specification({"only-if-cached.from.cache/request"})
    public void shouldReceiveCachedResponseWithOnlyIfCachedAndReachableCache() throws Exception {
        k3po.finish();
    }
}
