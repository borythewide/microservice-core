package kr.co.sys4u.msa.core.test.dto;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class TestDTO {
    @Min(10)
    private int number;
    @NotEmpty
    private String name;
    @Email
    private String email;

    public TestDTO(int number, String name, String email) {
        super();
        this.number = number;
        this.name = name;
        this.email = email;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
