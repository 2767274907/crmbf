package com.t248.lmf.crm.util;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.t248.lmf.crm.entity.Customer;
import com.t248.lmf.crm.entity.Orders;
import com.t248.lmf.crm.entity.OrdersLine;
import com.t248.lmf.crm.vo.DictInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class PdfUtil {

    public void createPDF(Document document, PdfWriter writer, List<Object> products) throws IOException {
        //Document document = new Document(PageSize.A4);
        try {
            document.addTitle("sheet of product");
            document.addAuthor("scurry");
            document.addSubject("product sheet.");
            document.addKeywords("product.");
            document.open();
            PdfPTable table = createTable(writer,products);
            document.add(table);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public static PdfPTable createTable(PdfWriter writer,List<Object> products) throws IOException, DocumentException {
    PdfPTable table = new PdfPTable(3);//生成一个两列的表格
    PdfPCell cell;
    int size = 20;
    Font font = new Font(BaseFont.createFont("C://Windows//Fonts//simfang.ttf", BaseFont.IDENTITY_H,
      BaseFont.NOT_EMBEDDED));
    for(int i = 0;i<products.size();i++) {
        if (products.get(i) instanceof Customer){
            Customer customer = (Customer) products.get(i);
            cell = new PdfPCell(new Phrase(String.valueOf(customer.getCustId()),font));//产品编号
            cell.setFixedHeight(size);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(customer.getCustName(),font));//产品名称
            cell.setFixedHeight(size);
            table.addCell(cell);
            Double ds = new Double(0);
            for (Orders orders:customer.getOrderss()){
                for (OrdersLine line:
                        orders.getOrdersLines()) {
                    ds+=line.getOddPrice();
                }
            }
            cell = new PdfPCell(new Phrase(ds+"",font));//产品价格
            cell.setFixedHeight(size);
            table.addCell(cell);
        }else{
            DictInfo dictInfo = (DictInfo) products.get(i);
            cell = new PdfPCell(new Phrase(String.valueOf(i),font));//产品编号
            cell.setFixedHeight(size);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(dictInfo.getName()),font));//产品编号
            cell.setFixedHeight(size);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(String.valueOf(dictInfo.getCount()),font));//产品编号
            cell.setFixedHeight(size);
            table.addCell(cell);
        }
    }
    return table;
  }
}