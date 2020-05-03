package dw.dw.dw.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, dw.dw.dw.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, dw.dw.dw.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, dw.dw.dw.domain.User.class.getName());
            createCache(cm, dw.dw.dw.domain.Authority.class.getName());
            createCache(cm, dw.dw.dw.domain.User.class.getName() + ".authorities");
            createCache(cm, dw.dw.dw.domain.PersistentToken.class.getName());
            createCache(cm, dw.dw.dw.domain.User.class.getName() + ".persistentTokens");
            createCache(cm, dw.dw.dw.domain.Vitalidade.class.getName());
            createCache(cm, dw.dw.dw.domain.ACES.class.getName());
            createCache(cm, dw.dw.dw.domain.ACES.class.getName() + ".doenteIdentidades");
            createCache(cm, dw.dw.dw.domain.Doente.class.getName());
            createCache(cm, dw.dw.dw.domain.Doente.class.getName() + ".horarioDoentes");
            createCache(cm, dw.dw.dw.domain.Doente.class.getName() + ".doenteDiagnosticoSocials");
            createCache(cm, dw.dw.dw.domain.Doente.class.getName() + ".doenteRegistosIntervencoes");
            createCache(cm, dw.dw.dw.domain.Doente.class.getName() + ".doenteHistMovimentos");
            createCache(cm, dw.dw.dw.domain.Doente.class.getName() + ".doenteContactosOutros");
            createCache(cm, dw.dw.dw.domain.DoenteIdentidade.class.getName());
            createCache(cm, dw.dw.dw.domain.DoenteIdentidade.class.getName() + ".pais");
            createCache(cm, dw.dw.dw.domain.Pais.class.getName());
            createCache(cm, dw.dw.dw.domain.DoenteContactos.class.getName());
            createCache(cm, dw.dw.dw.domain.DoenteContactosOutros.class.getName());
            createCache(cm, dw.dw.dw.domain.SitProf.class.getName());
            createCache(cm, dw.dw.dw.domain.SitProf.class.getName() + ".doenteSocioFamiliars");
            createCache(cm, dw.dw.dw.domain.Profissao.class.getName());
            createCache(cm, dw.dw.dw.domain.Profissao.class.getName() + ".doenteSocioFamiliars");
            createCache(cm, dw.dw.dw.domain.DoenteSocioFamiliar.class.getName());
            createCache(cm, dw.dw.dw.domain.DoenteDiagnosticoSocial.class.getName());
            createCache(cm, dw.dw.dw.domain.DoenteRegistosIntervencoes.class.getName());
            createCache(cm, dw.dw.dw.domain.DoenteHistMovimentos.class.getName());
            createCache(cm, dw.dw.dw.domain.HorarioDoente.class.getName());
            createCache(cm, dw.dw.dw.domain.SubSistemas.class.getName());
            createCache(cm, dw.dw.dw.domain.SubSisGrupo.class.getName());
            createCache(cm, dw.dw.dw.domain.Turnos.class.getName());
            createCache(cm, dw.dw.dw.domain.Turnos.class.getName() + ".doentes");
            createCache(cm, dw.dw.dw.domain.CentroSaude.class.getName());
            createCache(cm, dw.dw.dw.domain.CentroSaude.class.getName() + ".doenteIdentidades");
            createCache(cm, dw.dw.dw.domain.HospRef.class.getName());
            createCache(cm, dw.dw.dw.domain.HospRef.class.getName() + ".doenteIdentidades");
            createCache(cm, dw.dw.dw.domain.Habit.class.getName());
            createCache(cm, dw.dw.dw.domain.Habit.class.getName() + ".doenteSocioFamiliars");
            createCache(cm, dw.dw.dw.domain.GrauConf.class.getName());
            createCache(cm, dw.dw.dw.domain.GrauConf.class.getName() + ".doenteSocioFamiliars");
            createCache(cm, dw.dw.dw.domain.UserExtra.class.getName());
            createCache(cm, dw.dw.dw.domain.UserProfile.class.getName());
            createCache(cm, dw.dw.dw.domain.UserProfile.class.getName() + ".userExtras");
            createCache(cm, dw.dw.dw.domain.UserPermissions.class.getName());
            createCache(cm, dw.dw.dw.domain.RegistoData.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }

}
