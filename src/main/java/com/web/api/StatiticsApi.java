package com.web.api;

import com.web.enums.PayType;
import com.web.enums.Status;
import com.web.repository.InvoiceRepository;
import com.web.repository.ProductRepository;
import com.web.repository.UserRepository;
import com.web.utils.Contains;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/statistic")
@CrossOrigin
public class StatiticsApi {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/admin/revenue-this-month")
    public Double doanhThuThangNay(){
        Date date = new Date(System.currentTimeMillis());
        String[] str = date.toString().split("-");
        Integer year = Integer.valueOf(str[0]);
        Integer month = Integer.valueOf(str[1]);
        return invoiceRepository.calDt(month, year, Status.DA_NHAN, PayType.PAYMENT_MOMO);
    }

    @GetMapping("/admin/revenue-today")
    public Double revenueByDate(){
        Date date = new Date(System.currentTimeMillis());
        return invoiceRepository.revenueByDate(date,Status.DA_NHAN, PayType.PAYMENT_MOMO);
    }

    @GetMapping("/admin/number-invoice-today-finish")
    public Double numInvoiceToDay(){
        Date date = new Date(System.currentTimeMillis());
        return invoiceRepository.numInvoiceToDay(date,Status.DA_NHAN);
    }

    @GetMapping("/admin/number-admin")
    public Double numberAdmin(){
        return userRepository.countAdmin(Contains.ROLE_ADMIN);
    }

    @GetMapping("/admin/number-product")
    public Long numberProduct(){
        return productRepository.count();
    }

    @GetMapping("/admin/revenue-year")
    public List<Double> doanhThu(@RequestParam("year") Integer year){
        List<Double> list = new ArrayList<>();
        for(int i=1; i< 13; i++){
            Double sum = invoiceRepository.calDt(i, year, Status.DA_NHAN, PayType.PAYMENT_MOMO);
            if(sum == null){
                sum = 0D;
            }
            list.add(sum);
        }
        return list;
    }
}
