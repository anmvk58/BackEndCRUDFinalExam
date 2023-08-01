package com.vti.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.vti.dto.account.AccountDTO;
import com.vti.dto.account.DepartmentDTO;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.form.account.AccountFilterForm;
import com.vti.form.account.CreatingAccountForm;
import com.vti.service.IAccountService;

@RestController
@RequestMapping(value = "api/v1/accounts")
public class AccountController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private IAccountService service;

	@GetMapping()
	public Page<AccountDTO> getAllAccounts(Pageable pageable,
			@RequestParam(value = "search", required = false) String search, AccountFilterForm filterForm) {

		Page<Account> entityPages = service.getAllAccounts(pageable, search, filterForm);

		// convert entities --> dtos
		List<AccountDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<AccountDTO>>() {
		}.getType());

		Page<AccountDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;

	}

	@GetMapping(value = "/{id}")
	public AccountDTO getAccountById(@PathVariable(name = "id") int id){
		return service.getAccountById(id);
	}
	
	@GetMapping("/departments")
	public Page<DepartmentDTO> getAllDepartmentsForSearch(Pageable pageable,
			@RequestParam(value = "search", required = false) String search) {

		Page<Department> entityPages = service.getAllDepartmentsForSearch(pageable, search);

		// convert entities --> dtos
		List<DepartmentDTO> dtos = modelMapper.map(entityPages.getContent(), new TypeToken<List<DepartmentDTO>>() {
		}.getType());

		Page<DepartmentDTO> dtoPages = new PageImpl<>(dtos, pageable, entityPages.getTotalElements());

		return dtoPages;

	}

	@PostMapping()
	public void createAccount(@RequestBody CreatingAccountForm form) {
		service.createAccount(form);
	}

	@PutMapping(value = "/{id}")
	public void updateAccount(@PathVariable(name = "id") int id, @RequestBody CreatingAccountForm form){
		service.updateAccount(form, id);
	}
	
	@GetMapping(value = "/username/exists/{username}")
	public boolean existsByName(@PathVariable(name = "username") String username) {
		return service.isAccountExistsByUserName(username);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteAccount(@PathVariable(name = "id") int id) {
		service.deleteAccount(id);
	}
	
	@DeleteMapping
	public void deleteAccounts(@RequestParam(name = "ids") List<Integer> ids) {
		service.deleteAccounts(ids);
	}


}
