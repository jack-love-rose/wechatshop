//package com.imooc.wechatshop.utils.otherUtils;
//
//public class RSATester {
////    static String publicKey = "";
////    static String privateKey = "";
//    static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAviqX/GnW+AtRHTFMySL+\n" +
//        "P1or9OM4+pvaXcEGd86u/7Rro2umR8l6eD0iBZ6XiW6UzMp+2ax25bsA8dZCtrpq\n" +
//        "P3GODAzUt+cVyx0pJv0FRat8w3HIuZv0qQnz7Ty9vQ2FZfcAmD92qBPQocSMj6hG\n" +
//        "akfi7w8+NcGIiRrQ9L19a2EzCbjhdBtJd1bpTFCKS7yi5ytoygSx8ZVMu3KvmD8z\n" +
//        "2po0vQoTDSZSm6VMjOVhhdZ/o5XsDGEhh0NdmAtMaIw5q6WuFsWtFXp5bZM1oxIT\n" +
//        "tGpcD44iO4J2t8bd9OLweZfWAaTFK1/vrqPgtuTcGluyrkbyrRxNd1qER23u+wey\n" +
//        "+QIDAQAB";
//
//    static String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC+Kpf8adb4C1Ed\n" +
//            "MUzJIv4/Wiv04zj6m9pdwQZ3zq7/tGuja6ZHyXp4PSIFnpeJbpTMyn7ZrHbluwDx\n" +
//            "1kK2umo/cY4MDNS35xXLHSkm/QVFq3zDcci5m/SpCfPtPL29DYVl9wCYP3aoE9Ch\n" +
//            "xIyPqEZqR+LvDz41wYiJGtD0vX1rYTMJuOF0G0l3VulMUIpLvKLnK2jKBLHxlUy7\n" +
//            "cq+YPzPamjS9ChMNJlKbpUyM5WGF1n+jlewMYSGHQ12YC0xojDmrpa4Wxa0Venlt\n" +
//            "kzWjEhO0alwPjiI7gna3xt304vB5l9YBpMUrX++uo+C25NwaW7KuRvKtHE13WoRH\n" +
//            "be77B7L5AgMBAAECggEAJh6Bot5Qch5KnzBvtO3nyyk3IHqfxFSTfDGiK0fp+mXf\n" +
//            "Ib4FceRoQUv30uqMc8cP2tFIhje1Ca3nrZKGgvxxKdCIac/0n/yZeMKaqi8T7HhI\n" +
//            "pSDc3J/vdicBl2+SlR/tupe9rcci8OQv8Q1mI7HWk51fVw5WKQ5nYRxYAIcvaeOU\n" +
//            "Q1xQ048UIgeQpowFG6jewuYeGdy3BNVnHhW4PqJrbU2TQRVqwpWgNfFkepsMSZOi\n" +
//            "Vo4LiZq2jTUBzakaMBPhMcrSlH7chUCT/EvXs/yPzYU4qG0GZZCm7R0cFDMVi83C\n" +
//            "FIB4ptebSNqOHjyxP1KMPUk0pzlCLvcCGDbGt0d2pQKBgQDv/zoG8gHXmeVH+u9A\n" +
//            "Ga0KIRXUwKFdL5EB1SG4ieRvUCwvpfePV0D8Gt10Zoo3NdKYfncKvUBQWNT/q+qF\n" +
//            "xMRMLAw9m4ZiZuYorl44OT//mVq2n8rx37L0H77AxSJgKDweX48sUh8BQQR4gRdf\n" +
//            "GoOmZC+rrrQVVOWTr3ITqiEfSwKBgQDK2MDpIDfjr6fKZOmRmhomhrwOJGuElm+M\n" +
//            "t5H8kGDEvbMrDq4K+CnWqFjMrAu4Fl/YDvTdTTatrX6eaUFrZfyOgYc7N6hsaWEZ\n" +
//            "XhFjI3ezwrnwbeDe+Ds5ccczAYGqMtrzKSM8JKTCsMcfPFClPVlGRC1+XggBGV9E\n" +
//            "6Mx3P92YSwKBgGvGy1FJJwiihPPBPeYkYbAV0Y78frqxXMZc50rPKfXPJDi1qKoH\n" +
//            "fhh5HBBk6BcJJazzxhPdj19FtUmn86blCV5HvauBB2VzXlLQk2FeXF41iSZQNjxO\n" +
//            "wDefdfNt1BUonG7rbQlzS5ctkotnNY6xAoQbo8jOqPKXcI3VET7BfSRnAoGAMVVk\n" +
//            "HY6zaRG4ijXexIWpJIOSSxI6gAWiXaPrwnROxKqqX6dzVsGiU02gQy74rq3qY37H\n" +
//            "uObljHnsmJRaz8b/FJ2WCSXxNWSvs9639deTbknGtR3UGgZfSQpN+y67nWw+6fKw\n" +
//            "WhlDZQJfe05NAtMX2koMknZGFhZYoEbKK5qBx18CgYB44n3//FfHoKYrCCACWpxp\n" +
//            "vJ7T7dlns0+oU2EWZW43rza41P9TJI/EK9ltsQ+ayXgU//wGMHtKvZQxlOeW4p0d\n" +
//            "oNlvJ/FlGF6V3dcSWqe92ZNcV1oP8Sn0b84UYDQL+2w9rfsPEPMtqnby5hvzm0G1\n" +
//            "yBY53CUXiuYx6WaleQNSeA==";
//
////    static {
////        try {
////            Map<String, Object> keyMap = RSAUtils.genKeyPair();
////            publicKey = RSAUtils.getPublicKey(keyMap);
////            privateKey = RSAUtils.getPrivateKey(keyMap);
////            System.err.println("公钥: \n\r" + publicKey);
////            System.err.println("私钥： \n\r" + privateKey);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    public static void main(String[] args) throws Exception {
//        test();
////        testSign();
////        testHttpSign();
//    }
//
//    static void test() throws Exception {
//        System.err.println("公钥加密——私钥解密");
//        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
//        System.out.println("\r加密前文字：\r\n" + source);
//        byte[] data = source.getBytes();
//        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
//        System.out.println("加密后文字：\r\n" + new String(encodedData));
//        byte[] decodedData = RSAUtils.decryptByPrivateKey(encodedData, privateKey);
//        String target = new String(decodedData);
//        System.out.println("解密后文字: \r\n" + target);
//    }
//
//    static void testSign() throws Exception {
//        System.err.println("私钥加密——公钥解密");
//        String source = "这是一行测试RSA数字签名的无意义文字";
//        System.out.println("原文字：\r\n" + source);
//        byte[] data = source.getBytes();
//        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
//        System.out.println("加密后：\r\n" + new String(encodedData));
//        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
//        String target = new String(decodedData);
//        System.out.println("解密后: \r\n" + target);
//        System.err.println("私钥签名——公钥验证签名");
//        String sign = RSAUtils.sign(encodedData, privateKey);
//        System.err.println("签名:\r" + sign);
//        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
//        System.err.println("验证结果:\r" + status);
//    }
//
//    static void testHttpSign() throws Exception {
//        String param = "id=1&name=张三";
//        byte[] encodedData = RSAUtils.encryptByPrivateKey(param.getBytes(), privateKey);
//        System.out.println("加密后：" + encodedData);
//
//        byte[] decodedData = RSAUtils.decryptByPublicKey(encodedData, publicKey);
//        System.out.println("解密后：" + new String(decodedData));
//
//        String sign = RSAUtils.sign(encodedData, privateKey);
//        System.err.println("签名：" + sign);
//
//        boolean status = RSAUtils.verify(encodedData, publicKey, sign);
//        System.err.println("签名验证结果：" + status);
//    }
//}
