package org.alexk.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.alexk.businessLogic.OrderBL;
import org.alexk.businessLogic.OrderItemBL;
import org.alexk.models.viewmodels.OrderItemViewModel;
import org.alexk.models.viewmodels.OrderViewModel;

import java.io.FileOutputStream;

/**
 * Clasa care faciliteaza crearea unui document PDF cu comenzile finalizate
 */
public class PDFLogger {
    /**
     * String ce reprezinta calea catre locul unde vor fi salvate documentele PDF
     */
    private final static String path = "./bills/";

    /**
     * Blocheaza crearea instantelor
     */
    private PDFLogger() {

    }

    /**
     * @param id id-ul comenzii pentru care va fi creat documentul PDF
     */
    @SneakyThrows
    public static void createBill(int id) {
        val logic = new OrderBL();
        val order = logic.findViewModelById(id);
        createBill(order);
    }

    /**
     * @param orderViewModel datele de vizualizare despre comanda pentru care va fi creat documentul PDF
     */
    @SneakyThrows
    public static void createBill(OrderViewModel orderViewModel) {
        @Cleanup val pdf = new Document();
        PdfWriter.getInstance(pdf, new FileOutputStream(path + "Bill_" + orderViewModel.getId() + ".pdf"));

        pdf.open();

        val font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        val sb = new StringBuilder("FACTURA No. " + orderViewModel.getId());
        sb  .append("\nData: " + orderViewModel.getDate())
                .append("\nClient: " + orderViewModel.getCustomerName() + " ( " + orderViewModel.getCustomerId() + " )")
                .append("\nSum: " + orderViewModel.getPrice())
                .append("\n\nProduse: ");

        pdf.add(new Paragraph(sb.toString(), font));
        pdf.add(new Paragraph(" "));

        val table = createTable(orderViewModel);

        pdf.add(table);
    }

    /**
     * @param orderViewModel datele de vizualizare despre comanda pentru care va fi creat tabelul
     * @return un tabel ce contine toate produsele din comanda specificata
     * @throws Exception cand apare o eroare
     */
    private static PdfPTable createTable(OrderViewModel orderViewModel) throws Exception {
        val fields = FieldRetriever.getFields(OrderItemViewModel.class);
        val table = new PdfPTable(fields.size());

        for(val field : fields) {
            val title = StringFormatter.capitalize(StringFormatter.splitCamelCase(field.getName()));
            val header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(title));
            table.addCell(header);
        }

        val itemsBL = new OrderItemBL();
        itemsBL.findAllViewModelsByOrderId(orderViewModel.getId()).stream().forEach(item -> {
            for(val field : FieldRetriever.getFields(item.getClass())) {
                field.setAccessible(true);
                try {
                    table.addCell(field.get(item).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        return table;
    }
}
