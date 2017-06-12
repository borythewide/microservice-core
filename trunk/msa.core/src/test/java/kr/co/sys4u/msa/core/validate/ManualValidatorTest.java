package kr.co.sys4u.msa.core.validate;

import static kr.co.sys4u.msa.core.validate.ManualValidator.*;
import static org.junit.Assert.*;

import org.junit.Test;

import kr.co.sys4u.msa.core.exception.BadRequestException;
import kr.co.sys4u.msa.core.test.dto.TestDTO;

public class ManualValidatorTest {
    @Test(expected = BadRequestException.class)
    public void testNotNull() {
        // this will pass
        notNull("");
        notNull(new Object());

        // this will throw
        notNull(null);
    }

    @Test
    public void testNotEmpty() {
        // this will pass
        notEmpty("abc", true);
        notEmpty("   ", false);

        // this will throw
        try {
            notEmpty("", false);
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            notEmpty("   ", true);
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            notEmpty(null, false);
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testValidEmail() {
        // this will pass
        validEmail("borythewide@sys4u");
        validEmail("borythewide@sys4u.co.kr");
        validEmail("borythewide@gmail.com");
        validEmail("borythewide@naver.com");

        // this will throw
        try {
            validEmail("");
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }

        try {
            validEmail("borythewide");
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }

        try {
            validEmail("borythewide@");
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }

        try {
            validEmail("borythewide@.sys4u.co.kr");
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testValidate() {
        TestDTO valid = new TestDTO(10, "name", "borythewide@sys4u.co.kr");
        TestDTO invalid1 = new TestDTO(9, "name", "borythewide@sys4u.co.kr");
        TestDTO invalid2 = new TestDTO(10, "", "borythewide@sys4u.co.kr");
        TestDTO invalid3 = new TestDTO(10, null, "borythewide@sys4u.co.kr");
        TestDTO invalid4 = new TestDTO(10, "name", "borythewide");

        validate(valid);
        try {
            validate(invalid1);
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            validate(invalid2);
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            validate(invalid3);
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
        try {
            validate(invalid4);
            fail();
        } catch (Exception e) {
            assertNotNull(e);
        }
    }
}
