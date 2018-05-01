package com.example.employee.repository;

import com.example.employee.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    //以下所有的*都代表变量

    //1.查询名字是*的第一个employee
    Employee findFirstByName(String name);

    //2.找出Employee表中第一个姓名包含`*`字符并且薪资大于*的雇员个人信息
    Employee findFirstByNameContainingAndSalaryGreaterThan(String character,Integer salary);

    //3.找出一个薪资最高且公司ID是*的雇员以及该雇员的姓名
    @Query(value = "select e.name from Employee e where e.companyId = ?1  order by salary DESC limit 1;",nativeQuery = true)
    Employee findHighestSalaryInCompanyName(Integer companyId);

    //4.实现对Employee的分页查询，每页两个数据
    @Override
    Page<Employee> findAll(Pageable pageable);

    //5.查找**的所在的公司的公司名称
    @Query(value = "select c.companyName from Company c,Employee e where e.name = ?1 " +
            "and e.companyId = c.id",nativeQuery = true)
    String findCompanyNameByName(String name);

    //6.将*的名字改成*,输出这次修改影响的行数
    @Query(value = "update Employee e set e.name=?1 where e.name=?2 ", nativeQuery = true)
    @Modifying
    int modifyName(String newName,String oldName);


    //7.删除姓名是*的employee
    Employee deleteByName(String name);
}
