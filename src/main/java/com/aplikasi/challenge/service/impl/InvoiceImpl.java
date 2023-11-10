package com.aplikasi.challenge.service.impl;

import com.aplikasi.challenge.entity.OrderDetail;
import com.aplikasi.challenge.entity.Users;
import com.aplikasi.challenge.repository.UserRepository;
import com.aplikasi.challenge.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InvoiceImpl implements InvoiceService {
    @Autowired
    public UserRepository userRepository;
    @Override
    public byte[] generateInvoice(Users request) throws IOException, JRException {
        log.info("startMakingInvoice");
        Users users = userRepository.getById(request.getId());
        List<OrderDetail> orderDetailList = userRepository.getListOrderDetail(users.getId());

        JRDataSource dataSource = new JRBeanCollectionDataSource(orderDetailList);

        InputStream reportStream = new ClassPathResource("./report/User_Invoice.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("USER_ID", users.getId().toString());
        parameters.put("USERNAME", users.getUsername());
        parameters.put("USER_EMAIL", users.getEmailAddress());
        parameters.put("CREATED_DATE_INVOICE", new Date());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        log.info("finished making invoice");

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
