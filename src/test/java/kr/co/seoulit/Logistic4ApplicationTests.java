package kr.co.seoulit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.seoulit.hr.affair.serviceFacade.AffairServiceFacade;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class Logistic4ApplicationTests {

	@Autowired
	AffairServiceFacade affairSF;
	
	@BeforeAll
	static void before() {
		log.debug("===============================BEFORE=================================");
	}
	
	@AfterAll
	static void after() {
		log.debug("===============================AFTER=================================");
	}
	
	@BeforeEach
    void beforeEach() {
		log.debug("===============================BEFORE EACH=================================");
    }

    @AfterEach
    void afterEach() {
		log.debug("===============================AFTER EACH=================================");
    }
	@Test
	void contextLoads() {
		assertTrue(affairSF.checkEmpCodeDuplication("COM-01", "sh"));
	}
	@Test
	void test() {
		System.out.println(affairSF.getNewEmpCode("COM-01"));
	}
}
