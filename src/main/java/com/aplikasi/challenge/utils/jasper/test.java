//package com.aplikasi.challenge.utils.jasper;
//
//import com.aplikasi.challenge.entity.OrderDetail;
//import com.aplikasi.challenge.entity.Users;
//import com.aplikasi.challenge.repository.UserRepository;
//import com.aplikasi.challenge.service.InvoiceService;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.SQLException;
//import java.util.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class test {
//    @Autowired
//    public ReportService reportService;
//    @Autowired
//    public InvoiceService invoiceService;
//    @Autowired
//    public UserRepository userRepository;
//
//    @Test
//    public void test() throws SQLException, IOException, JRException {
//        UUID uuid = UUID.fromString("783b6a20-bd57-4937-9c07-0073fc6fc05f");
//        Users users = userRepository.getById(uuid);
//        List<OrderDetail> orderDetailList = userRepository.getListOrderDetail(users.getId());
//
//        JRDataSource dataSource = new JRBeanCollectionDataSource(orderDetailList);
//
//        InputStream reportStream = new ClassPathResource("./report/User_Invoice.jrxml").getInputStream();
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("USER_ID", users.getId().toString());
//        parameters.put("USERNAME", users.getUsername());
//        parameters.put("USER_EMAIL", users.getEmailAddress());
//        parameters.put("CREATED_DATE_INVOICE", new Date());
//
//        reportService.generate_pdf(parameters, reportStream, dataSource);
//    }
//}
