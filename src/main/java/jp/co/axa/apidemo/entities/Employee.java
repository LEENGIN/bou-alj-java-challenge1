package jp.co.axa.apidemo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {

    @Getter
    @Setter
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "EMPLOYEE_NAME")
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
    private String name;

    @Getter
    @Setter
    @NotNull(message = "salary is mandatory")
    @Min(value = 0, message = "salary should not be less than 0")
    @Max(value = Integer.MAX_VALUE, message = "salary should not be more than Integer.MAX_VALUE")
    private Integer salary;

    @Getter
    @Setter
    @Column(name = "DEPARTMENT")
    @NotBlank(message = "department is mandatory")
    @Size(min = 2, max = 255, message = "department must be between 2 and 255 characters")
    private String department;

}
