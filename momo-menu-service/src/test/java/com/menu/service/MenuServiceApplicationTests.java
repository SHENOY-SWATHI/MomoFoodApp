package com.menu.service;

import com.menu.service.Repository.MenuRepo;
import com.menu.service.Service.MenuServiceImpl;
import com.menu.service.Utils.BooleanToStringConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MenuServiceApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceApplicationTests.class);

	@InjectMocks
	private MenuServiceImpl menuService;

	@Mock
	private MenuRepo nenuRepo;

	@InjectMocks
	BooleanToStringConverter booleanToStringConverter;

	@Test
	public void convertToDatabaseColumnTest() throws Exception {
		LOGGER.info("Entered convertToDatabaseColumnTest()");
		String test = booleanToStringConverter.convertToDatabaseColumn(true);
		assertThat(test).isEqualTo("Y");
	}

	@Test
	public void convertToEntityAttributeTest() throws Exception {
		LOGGER.info("Entered convertToEntityAttributeTest()");
		Boolean test = booleanToStringConverter.convertToEntityAttribute("N");
		assertThat(test).isEqualTo(false);
	}

}
