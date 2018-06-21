package com.windman.hellocustomview.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


public class Cvd {
    private static final String ENC_UTF8 = "UTF-8";
    public static final byte INOF_DESCRIPTION = (byte) 2;
    public static final byte INOF_MINICODE = (byte) 3;
    public static final byte INOF_NAME = (byte) 0;
    public static final byte INOF_PLATFORM = (byte) 4;
    public static final byte INOF_STYLE = (byte) 1;
    private static final boolean IS_DEBUG = false;
    public static final float RES_HDPI_MINSCALE = 1.5f;
    public static final float RES_MHDPI_MINSCALE = 1.0f;
    public static final float RES_XHDPI_MINSCALE = 2.0f;
    public static final float RES_XXHPI_MINSCALE = 3.0f;
    public static final float RES_XXXDPI_MINISCALE = 3.5f;
    public static final float RES_XXXHDPI_MINISCALE = 4.0f;

    public static final boolean save(String str, byte[] bArr) {
        try {
            File file = new File(str);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static final byte[] open(String str, boolean z) {
        File file = new File(str);
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[fileInputStream.available()];
                fileInputStream.read(bArr);
                fileInputStream.close();
                if (!z) {
                    return bArr;
                }
                file.delete();
                return bArr;
            } catch (Exception e) {
            } catch (Error e2) {
            }
        }
        return null;
    }

    public static final void delete(String str) {
        File file = new File(str);
        try {
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }
    }

    public static final byte[] load(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            if (open == null) {
                return null;
            }
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return bArr;
        } catch (Exception e) {
            return null;
        }
    }

    public static final String[] parseTxt(byte[] bArr) {
        try {
            int length = bArr.length;
            char[] cArr = new char[(length >> 1)];
            int i = 2;
            int i2 = 0;
            int i3 = 0;
            while (i < length) {
                char c = (char) (((bArr[i + 1] & 255) << 8) | (bArr[i] & 255));
                switch (c) {
                    case '\r':
                        i3++;
                        cArr[i2] = c;
                        i += 2;
                        break;
                    case '\\':
//                        if (bArr[i + 2] != AbsLinkHandler.REQUEST_IMECHECK) {
//                            cArr[i2] = c;
//                            break;
//                        }
                        cArr[i2] = '\n';
                        i += 2;
                        break;
                    default:
                        cArr[i2] = c;
                        break;
                }
                i2++;
                i += 2;
            }
            String[] strArr = new String[i3];
            int length2 = cArr.length;
            i3 = 0;
            i = 0;
            i2 = 0;
            int i4 = 0;
            while (i4 < length2 && cArr[i4] != '\u0000') {
                if (cArr[i4] == '\r') {
                    strArr[i2] = new String(cArr, i, i3);
                    i2++;
                    i = i4 + 1;
                    i3 = 0;
                } else {
                    i3++;
                }
                i4++;
            }
            return strArr;
        } catch (Exception e) {
            return null;
        }
    }

//    public static final void readInfo(byte[] bArr, String[] strArr) {
//        if (bArr != null) {
//            int i;
//            int length = bArr.length;
////            if ((bArr[0] & 255) == PreferenceKeys.PREF_KEY_SEARCHBOX_SUPPORT_VERSIONCODE && (bArr[1] & 255) == 187 && (bArr[2] & 255) == 191) {
////                i = 3;
////            } else {
////                i = 0;
////            }
//            Object obj = 1;
//            byte[] bArr2 = new byte[bArr.length];
//            byte b = (byte) 0;
//            String str = "name";
//            String str2 = "style";
//            String str3 = "supportplatform";
//            String str4 = DictionaryHeader.DICTIONARY_DESCRIPTION_KEY;
//            String str5 = "minimecode";
//            int i2 = 0;
//            String str6 = "";
//            int i3 = i;
//            while (i3 < length) {
//                Object obj2;
//                byte b2;
//                if (bArr[i3] == (byte) 61) {
//                    String str7;
//                    try {
//                        str7 = new String(bArr2, 0, b, "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        str7 = str6;
//                    }
//                    str6 = str7.toLowerCase(Locale.US);
//                    if (str6.equals(str)) {
//                        i = 0;
//                    } else if (str6.equals(str2)) {
//                        i = 1;
//                    } else if (str6.equals(str3)) {
//                        i = 4;
//                    } else if (str6.equals(str4)) {
//                        i = 2;
//                    } else if (str6.equals(str5)) {
//                        i = 3;
//                    } else {
//                        i = i2;
//                    }
//                    obj2 = 1;
//                    int i4 = i;
//                    b2 = (byte) 0;
//                    i2 = i4;
//                } else if (bArr[i3] == PIConsts.REQ_CODE_SHARE) {
//                    try {
//                        str6 = new String(bArr2, 0, b, "UTF-8");
//                    } catch (UnsupportedEncodingException e2) {
//                    }
//                    if (i2 == 0) {
//                        strArr[0] = str6;
//                    } else if (i2 == 1) {
//                        strArr[1] = str6;
//                    } else if (i2 == 4) {
//                        strArr[4] = str6;
//                    } else if (i2 == 2) {
//                        strArr[2] = str6;
//                    } else if (i2 == 3) {
//                        strArr[3] = str6;
//                    }
//                    b2 = (byte) 0;
//                    i2 = -1;
//                    obj2 = obj;
//                } else {
//                    if (bArr[i3] != (byte) 10) {
//                        if (bArr[i3] != (byte) 32) {
//                            obj = null;
//                        }
//                        if (obj == null) {
//                            bArr2[b] = bArr[i3];
//                            b2 = (byte) (b + 1);
//                            obj2 = obj;
//                        }
//                    }
//                    b2 = b;
//                    obj2 = obj;
//                }
//                i3++;
//                obj = obj2;
//                b = b2;
//            }
//            if (i2 == 4) {
//                try {
//                    str6 = new String(bArr2, 0, b, "utf-8");
//                } catch (Exception e3) {
//                }
//                strArr[4] = str6;
//            }
//        }
//    }

    public static final String getResPath(float f, boolean z) {
        return getResPath(f, z, false);
    }

    public static final String getResPath(float f, boolean z, boolean z2) {
        String str;
        if (z2 && f >= RES_XXXHDPI_MINISCALE) {
            str = "1440";
        } else if (z && f >= RES_XXHPI_MINSCALE) {
            str = "1080";
        } else if (f >= RES_XHDPI_MINSCALE) {
            str = "720";
        } else if (f >= RES_HDPI_MINSCALE) {
            str = "480";
        } else if (f >= 1.0f) {
            str = "320";
        } else {
            str = "240";
        }
        return str + '/';
    }

    public static final String getResPath(float f) {
        return getResPath(f, false);
    }

    public static final float checkScale(float f) {
        return checkScale(f, false);
    }

    public static final float checkScale(float f, boolean z) {
        float f2 = RES_XXXHDPI_MINISCALE;
        if (!z || f < RES_XXXHDPI_MINISCALE) {
            if (z && f >= RES_XXHPI_MINSCALE) {
                f2 = 3.3f;
            } else if (f >= RES_XHDPI_MINSCALE) {
                f2 = 2.25f;
            } else if (f >= RES_HDPI_MINSCALE) {
                f2 = RES_HDPI_MINSCALE;
            } else if (f >= 1.0f) {
                f2 = 1.0f;
            } else {
                f2 = 0.75f;
            }
        }
        f2 = f / f2;
        if (f2 <= 0.9f || f2 >= 1.25f) {
            return f2;
        }
        return 1.0f;
    }
}