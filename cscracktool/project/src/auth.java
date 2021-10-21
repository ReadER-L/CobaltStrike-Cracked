public class auth {
    public byte[] intToByteArray(int num){
        return new byte[] {
                (byte) ((num >> 24) & 0xFF),
                (byte) ((num >> 16) & 0xFF),
                (byte) ((num >> 8) & 0xFF),
                (byte) (num & 0xFF)
        };
    }

    public static void main(String[] args){
        auth authTest = new auth();
        int header = -889274157; //绕过检查header
        int num = 29999999; //绕过过期问题
        int watermark = 43; //是否水印，1是否
        int version = 50; //必须大于44
        byte[] bheader = authTest.intToByteArray(header);
        byte[] bnum = authTest.intToByteArray(num);
        byte[] bwatermark = authTest.intToByteArray(watermark);
        byte[] bversion = authTest.intToByteArray(version);

        //cs4.0的最后16位key.
        byte[] last16key = {27, -27, -66, 82, -58, 37, 92, 51, 85, -114,
                -118, 28, -74, 103, -53, 6 };
        //cs4.4的最后16位key.
        byte[] cs4_4lastkey = {94, -104, 25, 74, 1, -58, -76, -113, -91, -126, -90, -87, -4, -69, -110, -42};
        System.out.println();

    }
}