package com.example.mahesh.iintent;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class create_pdf extends AppCompatActivity {

    String cid;
    String cust_name,pdf_name;
    String servicedb, totaldb;
    String item1db, item2db, item3db, item4db, item5db;
    String qty1db, qty2db, qty3db, qty4db, qty5db;
    String amt1db, amt2db, amt3db, amt4db, amt5db;
    String timedb, datedb;

    EditText et_pdf_name;
    Button create_pdf_btn,share;
    ImageButton img_back;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    private static final String TAG = "PdfCreatorActivity";
    private File pdfFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pdf);

        img_back = findViewById(R.id.pdf_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //startActivity(new Intent(create_pdf.this,update_bill.class));
            }
        });

        share = findViewById(R.id.btn_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File docsFolder = new File(Environment.getExternalStorageDirectory() + "/JBE/"+pdf_name+".pdf");
               File pdff = new File(docsFolder.getAbsolutePath(), pdf_name + ".pdf");

                //Uri uri = Uri.fromFile(pdff);

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                Uri screenshotUri = Uri.parse(docsFolder.toString());
                sharingIntent.setType("*/*");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));

            }
        });



        create_pdf_btn = findViewById(R.id.btn_create_pdf);


        Intent receivedIntent = getIntent();

        cid = receivedIntent.getStringExtra("id2");

        cust_name = receivedIntent.getStringExtra("name2");

        datedb = receivedIntent.getStringExtra("date");
        timedb = receivedIntent.getStringExtra("time");
        servicedb = receivedIntent.getStringExtra("service");

        item1db = receivedIntent.getStringExtra("item1");
        item2db = receivedIntent.getStringExtra("item2");
        item3db = receivedIntent.getStringExtra("item3");
        item4db = receivedIntent.getStringExtra("item4");
        item5db = receivedIntent.getStringExtra("item5");

        qty1db = receivedIntent.getStringExtra("qty1");
        qty2db = receivedIntent.getStringExtra("qty2");
        qty3db = receivedIntent.getStringExtra("qty3");
        qty4db = receivedIntent.getStringExtra("qty4");
        qty5db = receivedIntent.getStringExtra("qty5");

        amt1db = receivedIntent.getStringExtra("amt1");
        amt2db = receivedIntent.getStringExtra("amt2");
        amt3db = receivedIntent.getStringExtra("amt3");
        amt4db = receivedIntent.getStringExtra("amt4");
        amt5db = receivedIntent.getStringExtra("amt5");

        totaldb = receivedIntent.getStringExtra("total");


        et_pdf_name = findViewById(R.id.et_pdf_name);

        String nm = cid + " - " + cust_name;
        et_pdf_name.setText(nm);


        create_pdf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

            }
        });




    }


    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();

            /*Handler mHandler= new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    Intent intent = new Intent(create_pdf.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            },1000);*/
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void createPdf() throws FileNotFoundException, DocumentException {


        pdf_name = et_pdf_name.getText().toString();

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/JBE");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Created a new directory for PDF");
        }

        pdfFile = new File(docsFolder.getAbsolutePath(), pdf_name + ".pdf");
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document();

        PdfWriter writer = PdfWriter.getInstance(document, output);

        Rectangle rect = new Rectangle(84, 60, 520, 800);
        writer.setBoxSize("art", rect);
        HeaderFooter event = new HeaderFooter();
        writer.setPageEvent(event);

        document.open();
        //document.add_bill(new Paragraph(et_cust_name.getText().toString()));

        Date currentime = Calendar.getInstance().getTime();
        SimpleDateFormat d = new SimpleDateFormat("EEEE");
        String day = d.format(currentime);

        Chunk c = new Chunk("Day : " + day);
        Paragraph p_day = new Paragraph(c);
        p_day.setAlignment(Paragraph.ALIGN_LEFT);

        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.BOLD, BaseColor.BLACK);

        Chunk c1 = new Chunk("JBE", f);
        Paragraph p1 = new Paragraph(c1);
        p1.setAlignment(Paragraph.ALIGN_CENTER);

        Chunk c2 = new Chunk("Electronics selling & repairing shop");

        Paragraph p2 = new Paragraph(c2);
        p2.setAlignment(Paragraph.ALIGN_CENTER);


        document.add(p_day);
        document.add(p1);
        document.add(p2);
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));


        Chunk cno = new Chunk("No. ");
        Chunk cno2 = new Chunk(String.valueOf(cid));

        Phrase phrase_no=new Phrase();
        phrase_no.add(cno);
        phrase_no.add(cno2);
        Paragraph pno = new Paragraph();
        pno.add(phrase_no);
        pno.setAlignment(Paragraph.ALIGN_LEFT);
        document.add(pno);



        Font fname = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.BOLD, BaseColor.BLACK);
        Chunk cname = new Chunk("Customer Name :- ", fname);
        Font fname2 = new Font(Font.FontFamily.HELVETICA, 18.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk cname2 = new Chunk(cust_name, fname2);
        Phrase phrase = new Phrase();
        phrase.add(cname);
        phrase.add(cname2);

        Paragraph pname = new Paragraph();
        pname.add(phrase);
        pname.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pname);


        Chunk cser = new Chunk("service:  ");
        Font ser_font = new Font(Font.FontFamily.UNDEFINED, 12.0f, Font.UNDERLINE, BaseColor.BLACK);
        Chunk cser2 = new Chunk(String.valueOf(servicedb),ser_font);
        document.add(new Paragraph(" "));


        Phrase phrase_ser=new Phrase();
        phrase_ser.add(cser);
        phrase_ser.add(cser2);
        Paragraph pser = new Paragraph();
        pser.add(phrase_ser);
        pser.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pser);

        Font ftitle = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
        Font frow = new Font(Font.FontFamily.TIMES_ROMAN, 12);

        Paragraph paragraph = new Paragraph("");


        //specify column widths
        float[] columnWidths = {1.5f, 7f, 2f, 3f};
        //create PDF table with the given widths
        PdfPTable table = new PdfPTable(columnWidths);
        // set table width a percentage of the page width
        table.setPaddingTop(20.0f);
        table.setWidthPercentage(90f);

        //insert column headings
        insertCell(table, "No", Element.ALIGN_CENTER, 1, ftitle);
        insertCell(table, "Item Name", Element.ALIGN_CENTER, 1, ftitle);
        insertCell(table, "Quantity", Element.ALIGN_CENTER, 1, ftitle);
        insertCell(table, "Amount", Element.ALIGN_CENTER, 1, ftitle);
        table.setHeaderRows(1);

        if (!amt1db.isEmpty()){
        insertCell(table, "1", Element.ALIGN_CENTER, 1, frow);
        insertCell(table, item1db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, qty1db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, amt1db, Element.ALIGN_CENTER, 1, frow);
        table.setHeaderRows(1);}

        if (!amt2db.isEmpty()){
            insertCell(table, "2", Element.ALIGN_CENTER, 1, frow);
        insertCell(table, item2db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, qty2db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, amt2db, Element.ALIGN_CENTER, 1, frow);
        table.setHeaderRows(1);}

        if (!amt3db.isEmpty()){
        insertCell(table, "3", Element.ALIGN_CENTER, 1, frow);
        insertCell(table, item3db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, qty3db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, amt3db, Element.ALIGN_CENTER, 1, frow);
        table.setHeaderRows(1);}

        if (!amt4db.isEmpty()){
            insertCell(table, "4", Element.ALIGN_CENTER, 1, frow);
        insertCell(table, item4db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, qty4db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, amt4db, Element.ALIGN_CENTER, 1, frow);
        table.setHeaderRows(1);}

        if (!amt5db.isEmpty()){
            insertCell(table, "5", Element.ALIGN_CENTER, 1, frow);
        insertCell(table, item5db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, qty5db, Element.ALIGN_CENTER, 1, frow);
        insertCell(table, amt5db, Element.ALIGN_CENTER, 1, frow);
        table.setHeaderRows(1);}



        //insert an empty row
        insertCell(table, "", Element.ALIGN_LEFT, 2, frow);

        insertCell(table, "Total", Element.ALIGN_CENTER, 1, frow);
        insertCell(table, totaldb, Element.ALIGN_CENTER, 1, frow);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        //add_bill the PDF table to the paragraph
        paragraph.add(table);
        // add_bill the paragraph to the document
        document.add(paragraph);
        document.close();

        toastMessage("Bill Generated");



    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font){

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if(text.trim().equalsIgnoreCase("")){
            cell.setMinimumHeight(10f);
        }
        //add_bill the call to the table
        table.addCell(cell);

    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }

    public class HeaderFooter extends PdfPageEventHelper {
        // EditText mContentEditText = (EditText) findViewById(R.id.edit_text_content);
        Date currentime = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dt = new SimpleDateFormat("hh:mm a");
        String formattedate = df.format(currentime);
        String formattetime = dt.format(currentime);

        public void onStartPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Date : " + formattedate), rect.getLeft(), rect.getTop(), 0);

            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Paragraph("Time : " + formattetime), rect.getRight(), rect.getTop(), 0);
            // ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER, new Phrase("Date :- "+mContentEditText.getText().toString()), rect.getRight(), rect.getTop(),0);

        }

        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Page 1"), rect.getRight(), rect.getBottom(), 0);
        }

    }


    private void toastMessage(String message) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show();
    }
}

