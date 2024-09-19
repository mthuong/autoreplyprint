package com.caysn.autoreplyprint.sample;

import android.app.Activity;
import android.graphics.Bitmap;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import java.text.SimpleDateFormat;
import java.util.Date;

class TestFunction {

    public static String[] testFunctionOrderedList = new String[]{
            "Test_Pos_SampleTicket_58MM_1",
            "Test_Pos_SampleTicket_80MM_1",
            "Test_Pos_SampleTicket_80MM_2",

            "Test_Port_Write",
            "Test_Port_Read",
            "Test_Port_Available",
            "Test_Port_SkipAvailable",
            "Test_Port_IsConnectionValid",

            "Test_Printer_GetPrinterInfo",
            "Test_Printer_ClearPrinterBuffer",
            "Test_Printer_ClearPrinterError",

            "Test_Pos_QueryPrintResult",

            "Test_Pos_KickOutDrawer",
            "Test_Pos_Beep",
            "Test_Pos_FeedAndHalfCutPaper",
            "Test_Pos_FullCutPaper",
            "Test_Pos_HalfCutPaper",
            "Test_Pos_Feed",

            "Test_Pos_PrintSelfTestPage",
            "Test_Pos_PrintText",
            "Test_Pos_PrintTextInUTF8",
            "Test_Pos_PrintTextInGBK",
            "Test_Pos_PrintTextInBIG5",
            "Test_Pos_PrintTextInShiftJIS",
            "Test_Pos_PrintTextInEUCKR",
            "Test_Pos_PrintBarcode",
            "Test_Pos_PrintQRCode",
            "Test_Pos_PrintQRCodeUseEpsonCmd",
            "Test_Pos_PrintDoubleQRCode",
            "Test_Pos_PrintPDF417BarcodeUseEpsonCmd",
            "Test_Pos_PrintRasterImageFromBitmap",
            "Test_Pos_PrintHorizontalLine",
            "Test_Pos_PrintHorizontalLineSpecifyThickness",
            "Test_Pos_PrintMultipleHorizontalLinesAtOneRow",
            "Test_Pos_SetMovementUnit",
            "Test_Pos_SetPrintAreaLeftMargin",
            "Test_Pos_SetPrintAreaWidth",
            "Test_Pos_SetPrintPosition",
            "Test_Pos_SetAlignment",
            "Test_Pos_SetTextScale",
            "Test_Pos_SetAsciiTextFontType",
            "Test_Pos_SetTextBold",
            "Test_Pos_SetTextUnderline",
            "Test_Pos_SetTextUpsideDown",
            "Test_Pos_SetTextWhiteOnBlack",
            "Test_Pos_SetTextRotate",
            "Test_Pos_SetTextLineHeight",
            "Test_Pos_SetAsciiTextCharRightSpacing",
            "Test_Pos_SetKanjiTextCharSpacing",

            "Test_Pos_ResetPrinter",
            "Test_Pos_SetPrintSpeed_20",
            "Test_Pos_SetPrintSpeed_50",
            "Test_Pos_SetPrintSpeed_100",
            "Test_Pos_SetPrintDensity_0",
            "Test_Pos_SetPrintDensity_7",
            "Test_Pos_SetPrintDensity_15",
    };

    public Activity ctx = null;

    private static int nPageID = 1;

    void Test_Pos_SampleTicket_58MM_1(Pointer h)
    {
        int paperWidth = 384;

        AutoReplyPrint.INSTANCE.CP_Pos_ResetPrinter(h);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "123xxstreet,xxxcity,xxxxstate\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Right);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "TEL 9999-99-9999  C#2\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_HCenter);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "yyyy-MM-dd HH:mm:ss");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);

        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "apples");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*6);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$10.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "grapes");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*6);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$20.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "bananas");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*6);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$30.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "lemons");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*6);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$40.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "oranges");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*7);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$100.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "Before adding tax");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*7);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$200.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "tax 5.0%");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*6);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$10.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        String line = "";
        for (int i = 0; i < paperWidth / 12; ++i)
            line += " ";
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextUnderline(h, 2);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, line);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextUnderline(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 1, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "total");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*2*7);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$190.00");
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 0, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "Customer's payment");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*7);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$200.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "Change");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, paperWidth - 12*6);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "$10.00");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);

        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeHeight(h, 60);
        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeUnitWidth(h, 3);
        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeReadableTextPosition(h, AutoReplyPrint.CP_Pos_BarcodeTextPrintPosition_BelowBarcode);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_UPCA, "12345678901");

        AutoReplyPrint.INSTANCE.CP_Pos_Beep(h, 1, 500);

        {
            Test_Pos_QueryPrintResult(h);
        }
    }

    void Test_Pos_SampleTicket_80MM_1(Pointer h)
    {
        int[] nLineStartPos = { 0, 201, 401};
        int[] nLineEndPos = { 200, 400, 575};

        {
            AutoReplyPrint.INSTANCE.CP_Pos_ResetPrinter(h);
            AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
            AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 2);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 2);
            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Right);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("服务台\r\n"));
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 2);

            int nStartPos = 0;
            int nEndPos = 120;
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLineSpecifyThickness(h, nStartPos, nEndPos, 3);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedDot(h, 10);
            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Left);
            AutoReplyPrint.INSTANCE.CP_Pos_SetTextBold(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 1, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 12);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("圆桌"));
            AutoReplyPrint.INSTANCE.CP_Pos_FeedDot(h, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 0, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedDot(h, 10);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLineSpecifyThickness(h, nStartPos, nEndPos, 3);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_SetTextBold(h, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("麻辣香锅（上梅林店）\r\n2018年2月7日15:51:00\r\n\r\n"));
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_HCenter);
            AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 1, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("\r\n15-D-一楼-大厅-散座\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Left);
            AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 0, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("\r\n扫码点餐订单\r\n店内用餐\r\n7人\r\n"));
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("\r\n热菜类\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 80);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("鱼香肉丝"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 200);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("1"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 480);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("¥23.50\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 80);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("麻辣鸡丝"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 200);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("1"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 480);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("¥23.50\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 0);

            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("凉菜类\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 80);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("凉拌腐竹"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 200);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("1"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 480);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("¥23.50\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 80);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("糖醋花生"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 200);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("1"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 480);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("¥23.50\r\n"));
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_FeedDot(h, 30);
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 80);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("消毒餐具"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 200);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("7"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 480);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("¥14.00\r\n"));
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 2);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("在线支付"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 480);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("¥114.00\r\n"));
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("备注\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetPrintAreaLeftMargin(h, 80);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("所有菜都不要放葱，口味要微辣。百事可乐不要加冰。上菜快点，太慢了！！\r\n\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetPrintAreaLeftMargin(h, 0);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_HCenter);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintQRCode(h, 0, AutoReplyPrint.CP_QRCodeECC_L,"麻辣香锅");
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("\r\n用心服务每一天\r\n40008083030\r\n\r\n"));
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, nLineStartPos.length, nLineStartPos, nLineEndPos);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_Beep(h, 3, 300);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedAndHalfCutPaper(h);
        }

        {
            Test_Pos_QueryPrintResult(h);
        }
    }

    void Test_Pos_SampleTicket_80MM_2(Pointer h)
    {
        {
            AutoReplyPrint.INSTANCE.CP_Printer_ClearPrinterBuffer(h);
            AutoReplyPrint.INSTANCE.CP_Pos_ResetPrinter(h);
            AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
            AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, 0, 575);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, 0, 575);
            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Left);
            AutoReplyPrint.INSTANCE.CP_Pos_SetTextBold(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("国药堂大药房(上海)限公司\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("测试自动售药机器\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("订单号:" + 999999999 + "\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("日期:" + "2019-07-31" + "\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, 0, 575);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, 0, 575);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("商品编码/商品名称/规格/厂家\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, 0, 575);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, 0, 575);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 0, 3000);
        }

        {
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("FM111111" + "/" + "藿香正气水" + "/" + "1.8g*16片" + "/" + "盒" + "/" + "999感冒灵修正药业" + "★运动员慎用" + "\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("数量" + 666));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 240);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("单价:" + 111));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 380);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("金额:" + "9999.99" + "\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("批号:" + "22222222"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 340);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("有效期至:" + "2020-22-22" + "\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, 0, 575);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 0);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("数量合计:" + 999999));
            AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 380);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("金额合计:" + 9999.99 + "\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);

            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Left);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("客服电话:4001-005-835\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_HCenter);
            AutoReplyPrint.INSTANCE.CP_Pos_SetPrintAreaLeftMargin(h, 20);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("因药品属于特殊商品，除药品质量原因外，药品一经售出，不得退换\r\n\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_SetPrintAreaLeftMargin(h, 20);

            AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_HCenter);
            AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeUnitWidth(h, 8);
            AutoReplyPrint.INSTANCE.CP_Pos_PrintQRCode(h, 0, AutoReplyPrint.CP_QRCodeECC_L, "https://m.yao123.com/static/hmkp/invoiceInfo.html");

            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);

            AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("盒马APP-我的-扫码-申请开发票。\r\n请在90天内申请开发票\r\n\r\n\r\n"));
            AutoReplyPrint.INSTANCE.CP_Pos_FeedAndHalfCutPaper(h);

            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
            AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 1);
        }

        {
            Test_Pos_QueryPrintResult(h);
        }
    }

    void Test_Port_Write(Pointer h)
    {
        byte cmd[] = { 0x12, 0x54 };
        if (AutoReplyPrint.INSTANCE.CP_Port_Write(h, cmd, cmd.length, 1000) != cmd.length)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Port_Read(Pointer h)
    {
        // send this cmd to query printer status
        byte cmd[] = { 0x10, 0x04, 0x01 };
        AutoReplyPrint.INSTANCE.CP_Port_SkipAvailable(h);
        if (AutoReplyPrint.INSTANCE.CP_Port_Write(h, cmd, cmd.length, 1000) == cmd.length) {
            byte status[] = new byte[1];
            if (AutoReplyPrint.INSTANCE.CP_Port_Read(h, status, 1, 2000) == 1) {
                TestUtils.showMessageOnUiThread(ctx, String.format("Status 0x%02X", status[0] & 0xff));
            } else {
                TestUtils.showMessageOnUiThread(ctx, "Read failed");
            }
        } else {
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
        }
    }

    void Test_Port_Available(Pointer h)
    {
        int available = AutoReplyPrint.INSTANCE.CP_Port_Available(h);
        TestUtils.showMessageOnUiThread(ctx, "available " + available);
    }

    void Test_Port_SkipAvailable(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Port_SkipAvailable(h);
    }

    void Test_Port_IsConnectionValid(Pointer h)
    {
        boolean valid = AutoReplyPrint.INSTANCE.CP_Port_IsConnectionValid(h);
        TestUtils.showMessageOnUiThread(ctx, "valid " + valid);
    }

    void Test_Printer_GetPrinterInfo(Pointer h) {
        String firmware_version = AutoReplyPrint.CP_Printer_GetPrinterFirmwareVersion_Helper.GetPrinterFirmwareVersion(h) + "\r\n";
        IntByReference width_mm = new IntByReference();
        IntByReference height_mm = new IntByReference();
        IntByReference dots_per_mm = new IntByReference();
        LongByReference printer_error_status = new LongByReference();
        LongByReference printer_info_status = new LongByReference();
        IntByReference printer_received_byte_count = new IntByReference();
        IntByReference printer_printed_page_id = new IntByReference();
        LongByReference timestamp_ms_printer_status = new LongByReference();
        LongByReference timestamp_ms_printer_received = new LongByReference();
        LongByReference timestamp_ms_printer_printed = new LongByReference();
        if (AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterResolutionInfo(h, width_mm, height_mm, dots_per_mm) &&
                AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterStatusInfo(h, printer_error_status, printer_info_status, timestamp_ms_printer_status) &&
                AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterReceivedInfo(h, printer_received_byte_count, timestamp_ms_printer_received) &&
                AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterPrintedInfo(h, printer_printed_page_id, timestamp_ms_printer_printed)) {
            Date dt_printer_status = new Date(timestamp_ms_printer_status.getValue());
            Date dt_printer_received = new Date(timestamp_ms_printer_received.getValue());
            Date dt_printer_printed = new Date(timestamp_ms_printer_printed.getValue());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str_printer_resolution = "Width: " + width_mm.getValue() + " Height: " + height_mm.getValue() + " DotsPerMM: " + dots_per_mm.getValue() + "\r\n";
            String str_printer_error_status = simpleDateFormat.format(dt_printer_status) + String.format(" Printer Error Status: 0x%04X\r\n", printer_error_status.getValue() & 0xffff);
            String str_printer_info_status = simpleDateFormat.format(dt_printer_status) + String.format(" Printer Error Status: 0x%04X\r\n", printer_info_status.getValue() & 0xffff);
            String str_printer_received = simpleDateFormat.format(dt_printer_received) + String.format(" Printer Received Byte Count: %d\r\n", printer_received_byte_count.getValue());
            String str_printer_printed = simpleDateFormat.format(dt_printer_printed) + String.format(" Printer Printed Page ID: %d\r\n", printer_printed_page_id.getValue());
            TestUtils.showMessageOnUiThread(ctx, firmware_version + str_printer_resolution + str_printer_error_status + str_printer_info_status + str_printer_received + str_printer_printed);
        }
    }

    void Test_Printer_ClearPrinterBuffer(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Printer_ClearPrinterBuffer(h);
    }

    void Test_Printer_ClearPrinterError(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Printer_ClearPrinterError(h);
    }

    void Test_Pos_QueryPrintResult(Pointer h)
    {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, nPageID++, 30000);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Print failed");
        else
            TestUtils.showMessageOnUiThread(ctx, "Print Success");
    }

    void Test_Pos_KickOutDrawer(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_KickOutDrawer(h, 0, 100, 100);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_KickOutDrawer(h, 1, 100, 100);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_Beep(Pointer h) {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_Beep(h, 3, 300);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_FeedAndHalfCutPaper(Pointer h) {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedAndHalfCutPaper(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_FullCutPaper(Pointer h) {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FullCutPaper(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_HalfCutPaper(Pointer h) {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_HalfCutPaper(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_Feed(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "12345678901234567890");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 4);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "12345678901234567890");
        AutoReplyPrint.INSTANCE.CP_Pos_FeedDot(h, 100);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintSelfTestPage(Pointer h)
    {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintSelfTestPage(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintText(Pointer h) {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "12345678901234567890\r\n");
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintTextInUTF8(Pointer h) {
        WString str = new WString(
                "1234567890\r\n" +
                        "abcdefghijklmnopqrstuvwxyz\r\n" +
                        "ΑΒΓΔΕΖΗΘΙΚ∧ΜΝΞΟ∏Ρ∑ΤΥΦΧΨΩ\r\n" +
                        "αβγδεζηθικλμνξοπρστυφχψω\r\n" +
                        "你好，欢迎使用！\r\n" +
                        "你號，歡迎使用！\r\n" +
                        "梦を见る事が出来なければ\r\n未来を変える事は出来ません\r\n" +
                        "왕관을 쓰려는자\r\n그 무게를 견뎌라\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, str);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintTextInGBK(Pointer h) {
        WString str = new WString("1234567890\r\nabcdefghijklmnopqrstuvwxyz\r\n你好，欢迎使用！\r\n你號，歡迎使用！\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_GBK);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInGBK(h, str);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintTextInBIG5(Pointer h) {
        WString str = new WString("1234567890\r\nabcdefghijklmnopqrstuvwxyz\r\n你號，歡迎使用！\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_BIG5);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInBIG5(h, str);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintTextInShiftJIS(Pointer h) {
        WString str = new WString(
                "1234567890\r\n" +
                        "abcdefghijklmnopqrstuvwxyz\r\n" +
                        "こんにちは\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_ShiftJIS);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInShiftJIS(h, str);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintTextInEUCKR(Pointer h) {
        WString str = new WString(
                "1234567890\r\n" +
                        "abcdefghijklmnopqrstuvwxyz\r\n" +
                        "왕관을 쓰려는자\r\n" +
                        "그 무게를 견뎌라\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_EUCKR);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInEUCKR(h, str);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintBarcode(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeUnitWidth(h, 2);
        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeHeight(h, 60);
        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeReadableTextFontType(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeReadableTextPosition(h, AutoReplyPrint.CP_Pos_BarcodeTextPrintPosition_BelowBarcode);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_UPCA, "01234567890");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_UPCE, "123456");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_EAN13, "123456789012");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_EAN8, "1234567");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_CODE39, "123456");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_ITF, "123456");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_CODEBAR, "A123456A");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_CODE93, "123456");
        AutoReplyPrint.INSTANCE.CP_Pos_PrintBarcode(h, AutoReplyPrint.CP_Pos_BarcodeType_CODE128, "No.123456");

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintQRCode(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetBarcodeUnitWidth(h, 8);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintQRCode(h, 0, AutoReplyPrint.CP_QRCodeECC_L, "Hello 欢迎使用");

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");

    }

    void Test_Pos_PrintQRCodeUseEpsonCmd(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_PrintQRCodeUseEpsonCmd(h, 8, AutoReplyPrint.CP_QRCodeECC_L, "Hello 欢迎使用");

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");

    }

    void Test_Pos_PrintDoubleQRCode(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_PrintDoubleQRCode(h, 4, 0, 4, AutoReplyPrint.CP_QRCodeECC_L, "hello", 200, 3, AutoReplyPrint.CP_QRCodeECC_L, "test");

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");

    }

    void Test_Pos_PrintPDF417BarcodeUseEpsonCmd(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_PrintPDF417BarcodeUseEpsonCmd(h, 0, 0, 3, 3, 0, 0, "test 测试");

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");

    }

    void Test_Pos_PrintRasterImageFromBitmap(Pointer h)
    {
        Bitmap bitmap = TestUtils.getImageFromAssetsFile(ctx, "RasterImage/blackwhite.png");
        if ((bitmap == null) || (bitmap.getWidth() == 0) || (bitmap.getHeight() == 0))
            return;

        int printwidth = 384;
        int dstw = printwidth;
        int dsth = (int) (dstw * ((double) bitmap.getHeight() / bitmap.getWidth()));

        boolean result = AutoReplyPrint.CP_Pos_PrintRasterImageFromData_Helper.PrintRasterImageFromBitmap(h, dstw, dsth, bitmap, AutoReplyPrint.CP_ImageBinarizationMethod_Thresholding, AutoReplyPrint.CP_ImageCompressionMethod_None);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_PrintHorizontalLine(Pointer h)
    {
        for (int i = 0; i < 50; i += 1)
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, i, i + 100);
        for (int i = 50; i > 0; i -= 1)
            AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLine(h, i, i + 100);

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }
    void Test_Pos_PrintHorizontalLineSpecifyThickness(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_PrintHorizontalLineSpecifyThickness(h, 0, 200, 10);

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }
    void Test_Pos_PrintMultipleHorizontalLinesAtOneRow(Pointer h)
    {
        int r = 150;
        for (int y = -r; y <= r; ++y) {
            int x = (int) Math.sqrt(r*r-y*y);
            int x1 = -x+r;
            int x2 = x+r;
            int[] pLineStartPosition = new int[] { x1, x2 };
            int[] pLineEndPosition = new int[] { x1, x2 };
            if (AutoReplyPrint.INSTANCE.CP_Pos_PrintMultipleHorizontalLinesAtOneRow(h, 2, pLineStartPosition, pLineEndPosition))
                continue;
            else
                break;
        }

        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetMovementUnit(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetMovementUnit(h, 100, 100);
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextCharRightSpacing(h, 10);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "1234567890\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMovementUnit(h, 200, 200);
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextCharRightSpacing(h, 10);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "1234567890\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextCharRightSpacing(h, 0);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetPrintAreaLeftMargin(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintAreaLeftMargin(h, 96);
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintAreaWidth(h, 384);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "1234567890123456789012345678901234567890\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_ResetPrinter(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetPrintAreaWidth(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintAreaWidth(h, 384);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "1234567890123456789012345678901234567890\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_ResetPrinter(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetPrintPosition(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "12345678901234567890\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, 24);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "1234567890");
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalRelativePrintPosition(h, 24);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "1234567890\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetAlignment(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Right);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "12345678901234567890\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_HCenter);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "12345678901234567890\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAlignment(h, AutoReplyPrint.CP_Pos_Alignment_Left);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "12345678901234567890\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetTextScale(Pointer h) {
        int nPosition = 0;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 0, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        nPosition += 12;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 1, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        nPosition += 12 * 2;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 2, 2);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        nPosition += 12 * 3;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 3, 3);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        nPosition += 12 * 4;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 4, 4);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        nPosition += 12 * 5;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 5, 5);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        nPosition += 12 * 6;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 6, 6);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        nPosition += 12 * 7;
        AutoReplyPrint.INSTANCE.CP_Pos_SetHorizontalAbsolutePrintPosition(h, nPosition);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 7, 7);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "a");
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextScale(h, 0, 0);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetAsciiTextFontType(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextFontType(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "FontA\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextFontType(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "FontB\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextFontType(h, 2);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "FontC\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextFontType(h, 3);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "FontD\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextFontType(h, 4);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "FontE\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextFontType(h, 0);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetTextBold(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextBold(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("粗体 Bold\r\n"));
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextBold(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "Normal\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetTextUnderline(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextUnderline(h, 2);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("下划线2点 Underline2\r\n"));
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextUnderline(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("下划线1点 Underline2\r\n"));
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextUnderline(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("无下划线 No Underline\r\n"));
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetTextUpsideDown(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextUpsideDown(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("上下倒置 UpsideDown\r\n"));
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextUpsideDown(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "Normal\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetTextWhiteOnBlack(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextWhiteOnBlack(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("黑白反显 WhiteOnBlack\r\n"));
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextWhiteOnBlack(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "Normal\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetTextRotate(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextRotate(h, 1);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, new WString("文字旋转打印 TextRotate\r\n"));
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextRotate(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "Normal\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetTextLineHeight(Pointer h) {
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextLineHeight(h, 100);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "LineHeight 100\r\nLineHeight 100\r\nLineHeight 100\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetTextLineHeight(h, 32);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintText(h, "LineHeight 32\r\nLineHeight 32\r\nLineHeight 32\r\n");
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetAsciiTextCharRightSpacing(Pointer h) {
        WString str = new WString("Hello你好\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextCharRightSpacing(h, 2);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, str);
        AutoReplyPrint.INSTANCE.CP_Pos_SetAsciiTextCharRightSpacing(h, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, str);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetKanjiTextCharSpacing(Pointer h) {
        WString str = new WString("Hello你好\r\n");
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteMode(h);
        AutoReplyPrint.INSTANCE.CP_Pos_SetMultiByteEncoding(h, AutoReplyPrint.CP_MultiByteEncoding_UTF8);
        AutoReplyPrint.INSTANCE.CP_Pos_SetKanjiTextCharSpacing(h, 2, 2);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, str);
        AutoReplyPrint.INSTANCE.CP_Pos_SetKanjiTextCharSpacing(h, 0, 0);
        AutoReplyPrint.INSTANCE.CP_Pos_PrintTextInUTF8(h, str);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_FeedLine(h, 3);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_ResetPrinter(Pointer h)
    {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_ResetPrinter(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetPrintSpeed_20(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintSpeed(h, 20);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintSelfTestPage(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetPrintSpeed_50(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintSpeed(h, 50);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintSelfTestPage(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetPrintSpeed_100(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintSpeed(h, 100);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintSelfTestPage(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

    void Test_Pos_SetPrintDensity_0(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintDensity(h, 0);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintSelfTestPage(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }
    void Test_Pos_SetPrintDensity_7(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintDensity(h, 7);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintSelfTestPage(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }
    void Test_Pos_SetPrintDensity_15(Pointer h)
    {
        AutoReplyPrint.INSTANCE.CP_Pos_SetPrintDensity(h, 15);
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_PrintSelfTestPage(h);
        if (!result)
            TestUtils.showMessageOnUiThread(ctx, "Write failed");
    }

}
