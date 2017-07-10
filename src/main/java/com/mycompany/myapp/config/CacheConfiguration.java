package com.mycompany.myapp.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Transaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Book.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Asset.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Currency.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Market.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Zone.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Sector.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.HAsset.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.HCurrency.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.HBook.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Preference.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.People.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Calcul.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Fund.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
