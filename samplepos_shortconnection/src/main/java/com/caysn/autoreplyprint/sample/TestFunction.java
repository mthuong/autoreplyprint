package com.caysn.autoreplyprint.sample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.caysn.autoreplyprint.AutoReplyPrint;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import java.text.SimpleDateFormat;
import java.util.Date;

class TestFunction {

    public static class PortParam {
        boolean bBT2;
        boolean bBT4;
        boolean bNET;
        boolean bUSB;
        boolean bCOM;

        String strBT2Address;
        String strBT4Address;
        String strNETAddress;
        String strUSBPort;
        String strCOMPort;
        int nCOMBaudrate;
    }

    public static String[] testFunctionOrderedList = new String[]{
            "Test_Pos_SampleTicket_58MM_1",
            "Test_Pos_SampleTicket_80MM_1",
    };

    private static final String TAG = "TestFunction";

    private Pointer OpenPort(Activity ctx, PortParam port)
    {
        Pointer h = Pointer.NULL;
        if (port.bBT2) {
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenBtSpp(port.strBT2Address, 1);
        } else if (port.bBT4) {
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenBtBle(port.strBT4Address, 1);
        } else if (port.bNET) {
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenTcp(null, port.strNETAddress, (short) 9100, 5000, 1);
        } else if (port.bUSB) {
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenUsb(port.strUSBPort, 1);
        } else if (port.bCOM) {
            h = AutoReplyPrint.INSTANCE.CP_Port_OpenCom(port.strCOMPort, port.nCOMBaudrate, AutoReplyPrint.CP_ComDataBits_8, AutoReplyPrint.CP_ComParity_NoParity, AutoReplyPrint.CP_ComStopBits_One, AutoReplyPrint.CP_ComFlowControl_None, 1);
        }
        Log.i(TAG, h == Pointer.NULL ? "OpenPort Failed" : "OpenPort Success");
        if (h == Pointer.NULL) {
            TestUtils.showMessageOnUiThread(ctx, "OpenPort Failed");
        }
        return h;
    }

    private boolean QueryPrintResult(Activity ctx, Pointer h)
    {
        boolean result = AutoReplyPrint.INSTANCE.CP_Pos_QueryPrintResult(h, 0, 30000);
        Log.i(TAG, result ? "Print Success" : "Print Failed");
        TestUtils.showMessageOnUiThread(ctx, result ? "Print Success" : "Print Failed");
        if (!result) {
            LongByReference printer_error_status = new LongByReference();
            LongByReference printer__info_status = new LongByReference();
            LongByReference timestamp_ms_printer_status = new LongByReference();
            if (AutoReplyPrint.INSTANCE.CP_Printer_GetPrinterStatusInfo(h, printer_error_status, printer__info_status, timestamp_ms_printer_status)) {
                AutoReplyPrint.CP_PrinterStatus status = new AutoReplyPrint.CP_PrinterStatus(printer_error_status.getValue(), printer__info_status.getValue());
                String error_status_string = String.format("Printer Error Status: 0x%04X", printer_error_status.getValue() & 0xffff);
                if (status.ERROR_OCCURED()) {
                    if (status.ERROR_CUTTER())
                        error_status_string += "[ERROR_CUTTER]";
                    if (status.ERROR_FLASH())
                        error_status_string += "[ERROR_FLASH]";
                    if (status.ERROR_NOPAPER())
                        error_status_string += "[ERROR_NOPAPER]";
                    if (status.ERROR_VOLTAGE())
                        error_status_string += "[ERROR_VOLTAGE]";
                    if (status.ERROR_MARKER())
                        error_status_string += "[ERROR_MARKER]";
                    if (status.ERROR_ENGINE())
                        error_status_string += "[ERROR_ENGINE]";
                    if (status.ERROR_OVERHEAT())
                        error_status_string += "[ERROR_OVERHEAT]";
                    if (status.ERROR_COVERUP())
                        error_status_string += "[ERROR_COVERUP]";
                    if (status.ERROR_MOTOR())
                        error_status_string += "[ERROR_MOTOR]";
                }
                Log.i(TAG, error_status_string);
                TestUtils.showMessageOnUiThread(ctx, error_status_string);
            } else {
                Log.i(TAG, "CP_Printer_GetPrinterStatusInfo Failed");
                TestUtils.showMessageOnUiThread(ctx, "CP_Printer_GetPrinterStatusInfo Failed");
            }
        }
        return result;
    }

    void Test_Pos_SampleTicket_58MM_1(Activity ctx, PortParam port)
    {
        Pointer h = OpenPort(ctx, port);
        if (h != Pointer.NULL) {
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
                QueryPrintResult(ctx, h);
            }

            AutoReplyPrint.INSTANCE.CP_Port_Close(h);
        }
    }

    void Test_Pos_SampleTicket_80MM_1(Activity ctx, PortParam port)
    {
        Pointer h = OpenPort(ctx, port);
        if (h != Pointer.NULL) {
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
                QueryPrintResult(ctx, h);
            }

            AutoReplyPrint.INSTANCE.CP_Port_Close(h);
        }
    }

}
