package com.amitgroup.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amitgroup.services.maintenance.MaintenanceService;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;
import com.amitgroup.sqldatabase.entities.Area;
import com.amitgroup.sqldatabase.entities.Machine;
import com.amitgroup.sqldatabase.entities.Role;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.repositories.AreaRepository;
import com.amitgroup.sqldatabase.repositories.MachineRepository;
import com.amitgroup.sqldatabase.repositories.RoleRepository;
import com.amitgroup.sqldatabase.repositories.UserRepository;

@org.springframework.stereotype.Service
@RestController
public class InsertData {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private MachineRepository machineRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping("/insert")
    public void inserData() {

        try {
            Role role = new Role();
            role.setName("Quản lý");
            role.setCode("MNG");
            Role role2 = new Role();
            role2.setName("Giám sát");
            role2.setCode("SP");
            Role role3 = new Role();
            role3.setName("Thợ");
            role3.setCode("WK");
            Role role4 = new Role();
            role3.setName("Admin");
            role3.setCode("AD");
            roleRepository.save(role);
            roleRepository.save(role2);
            roleRepository.save(role3);
            roleRepository.save(role4);

            User user = new User();
            user.setFullName("Phan Nguyễn Đăng Trường");
            user.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user.setEmail("pndangtruong@gmail.com");
            user.setPhone("0987654331");
            user.setIsActive(true);
            user.setUsername("dangtruong");
            user.setRole(role);
            ;

            User user2 = new User();
            user2.setFullName("Nguyễn Chí Thành");
            user2.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user2.setEmail("truongdangphan@gmail.com");
            user2.setPhone("09876543221");
            user2.setIsActive(true);
            user2.setUsername("truong");
            user2.setRole(role2);
            ;

            User user3 = new User();
            user3.setFullName("Nguyễn Văn Thợ");
            user3.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user3.setEmail("nguyenvantho@gmail.com");
            user3.setPhone("0982654321");
            user3.setIsActive(true);
            user3.setUsername("dang");
            user3.setRole(role3);

            User user4 = new User();
            user4.setFullName("Phạm Thị Hạnh");
            user4.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user4.setEmail("phamthihanh@gmail.com");
            user4.setPhone("0987654321");
            user4.setIsActive(true);
            user4.setUsername("hanh");
            user4.setRole(role4);

            User user5 = new User();
            user5.setFullName("Trần Thị Mai");
            user5.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user5.setEmail("tranthimai@gmail.com");
            user5.setPhone("0987654322");
            user5.setIsActive(true);
            user5.setUsername("mai");
            user5.setRole(role3);

            User user6 = new User();
            user6.setFullName("Lê Thị Ngọc");
            user6.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user6.setEmail("lethingoc@gmail.com");
            user6.setPhone("0987654323");
            user6.setIsActive(true);
            user6.setUsername("ngoc");
            user6.setRole(role3);

            User user7 = new User();
            user7.setFullName("Nguyễn Thị Thúy");
            user7.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user7.setEmail("nguyenthithuy@gmail.com");
            user7.setPhone("0987654324");
            user7.setIsActive(true);
            user7.setUsername("thuy");
            user7.setRole(role3);

            User user8 = new User();
            user8.setFullName("Vũ Thị Hương");
            user8.setPassword(bCryptPasswordEncoder.encode("12345678"));
            user8.setEmail("vuthihuog@gmail.com");
            user8.setPhone("0987654325");
            user8.setIsActive(true);
            user8.setUsername("huong");
            user8.setRole(role3);

            userRepository.save(user);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);

            Machine machine = new Machine();
            machine.setCode("M001");
            machine.setName("Máy cắt");
            machine.setDescription("Máy cắt");
            machineRepository.save(machine);

            Machine machine2 = new Machine();
            machine2.setCode("M002");
            machine2.setName("Máy ảnh");
            machine2.setDescription("Máy ảnh");
            machineRepository.save(machine2);

            Machine machine3 = new Machine();
            machine3.setCode("M003");
            machine3.setName("Máy in");
            machine3.setDescription("Máy in");
            machineRepository.save(machine3);

            Area area = new Area();
            area.setName("Khu vực 1");
            area.setDescription("Khu vực 1");
            areaRepository.save(area);

            Area area2 = new Area();
            area2.setName("Khu vực 2");
            area2.setDescription("Khu vực 2");
            areaRepository.save(area2);

            Area area3 = new Area();
            area3.setName("Khu vực 3");
            area3.setDescription("Khu vực 3");
            areaRepository.save(area3);

            Date date = new Date();
            // set time 2023-04-10
            date.setYear(2023);
            date.setMonth(4);
            date.setDate(10);

            MaintenanceRequestDTO maintenance = new MaintenanceRequestDTO();
            maintenance.setAreaId(1L);
            maintenance.setMachineId(1L);
            maintenance.setDescription("Máy cắt bị hỏng");
            maintenance.setEndDate(date);
            maintenance.setContent("Máy cắt bị hỏng");

            MaintenanceRequestDTO maintenance1 = new MaintenanceRequestDTO();
            maintenance1.setAreaId(2L);
            maintenance1.setMachineId(2L);
            maintenance1.setDescription("Máy hàn bị hỏng");
            maintenance1.setEndDate(date);
            maintenance1.setContent("Máy hàn bị hỏng");

            MaintenanceRequestDTO maintenance2 = new MaintenanceRequestDTO();
            maintenance2.setAreaId(1L);
            maintenance2.setMachineId(1L);
            maintenance2.setDescription("Máy cắt bị mòn dao cắt");
            maintenance2.setEndDate(date);
            maintenance2.setContent("Máy cắt bị mòn dao cắt");

            MaintenanceRequestDTO maintenance3 = new MaintenanceRequestDTO();
            maintenance3.setAreaId(2L);
            maintenance3.setMachineId(2L);
            maintenance3.setDescription("Máy rửa bát bị chết điện");
            maintenance3.setEndDate(date);
            maintenance3.setContent("Máy rửa bát bị chết điện");

            MaintenanceRequestDTO maintenance4 = new MaintenanceRequestDTO();
            maintenance4.setAreaId(3L);
            maintenance4.setMachineId(3L);
            maintenance4.setDescription("Máy nén khí bị rò rỉ");
            maintenance4.setEndDate(date);
            maintenance4.setContent("Máy nén khí bị rò rỉ");

            MaintenanceRequestDTO maintenance5 = new MaintenanceRequestDTO();
            maintenance5.setAreaId(1L);
            maintenance5.setMachineId(1L);
            maintenance5.setDescription("Máy in bị kẹt giấy");
            maintenance5.setEndDate(date);
            maintenance5.setContent("Máy in bị kẹt giấy");

            MaintenanceRequestDTO maintenance6 = new MaintenanceRequestDTO();
            maintenance6.setAreaId(2L);
            maintenance6.setMachineId(2L);
            maintenance6.setDescription("Máy phun sơn bị hỏng đầu phun");
            maintenance6.setEndDate(date);
            maintenance6.setContent("Máy phun sơn bị hỏng đầu phun");

            MaintenanceRequestDTO maintenance7 = new MaintenanceRequestDTO();
            maintenance7.setAreaId(3L);
            maintenance7.setMachineId(3L);
            maintenance7.setDescription("Máy cơ khí bị rỉ sét");
            maintenance7.setEndDate(date);
            maintenance7.setContent("Máy cơ khí bị rỉ sét");

            MaintenanceRequestDTO maintenance8 = new MaintenanceRequestDTO();
            maintenance8.setAreaId(2L);
            maintenance8.setMachineId(3L);
            maintenance8.setDescription("Máy bơm bị chết động cơ");
            maintenance8.setEndDate(date);
            maintenance8.setContent("Máy bơm bị chết động cơ");

            MaintenanceRequestDTO maintenance9 = new MaintenanceRequestDTO();
            maintenance9.setAreaId(1L);
            maintenance9.setMachineId(1L);
            maintenance9.setDescription("Máy cắt plasma bị hỏng béc cắt");
            maintenance9.setEndDate(date);
            maintenance9.setContent("Máy cắt plasma bị hỏng béc cắt");

            MaintenanceRequestDTO maintenance10 = new MaintenanceRequestDTO();
            maintenance10.setAreaId(1L);
            maintenance10.setMachineId(1L);
            maintenance10.setDescription("Máy phay bị hỏng trục chính");
            maintenance10.setEndDate(date);
            maintenance10.setContent("Máy phay bị hỏng trục chính");

            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
