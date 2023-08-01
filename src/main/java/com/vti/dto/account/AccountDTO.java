package com.vti.dto.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDTO {

	private int id;

	private String username;

	private String firstName;

	private String lastName;

	private String fullName;

	private String role;

	private String departmentName;

	private int departmentId;
}
