package com.fnklabs.flene.application;

import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IgniteConfiguration.class, ApplicationTestConfiguration.class})
@ActiveProfiles(profiles = {SpringProfiles.TEST})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ApplicationTest {
    @Autowired
    private Ignite ignite;

    @Before
    public void setUp() throws Exception {
        ignite.getOrCreateCache(new CacheConfiguration<>("test.1"));
        ignite.getOrCreateCache(new CacheConfiguration<>("test.2"));
    }

    @After
    public void tearDown() throws Exception {
        ignite.getOrCreateCache(new CacheConfiguration<>("test.1")).close();
        ignite.getOrCreateCache(new CacheConfiguration<>("test.2")).close();
    }
}