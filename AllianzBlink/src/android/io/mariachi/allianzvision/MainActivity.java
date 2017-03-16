package io.mariachi.allianzvision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import io.mariachi.allianzvision.ui.Faces;
import io.mariachi.allianzvision.utils.Utils;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

public class MainActivity extends CordovaPlugin {
  public CallbackContext callbackContext;

  @Override
  public boolean execute(String action, JSONArray args,
                         CallbackContext callbackContext) {
      
      // TODO Auto-generated method stub
      this.callbackContext = callbackContext;
      
      try {
          if ("blinkMethod".equals(action)) {
            Intent ca = new Intent(this.cordova.getActivity().getApplicationContext(), Faces.class);
            cordova.getActivity().startActivity(ca);
            PluginResult faceBlink = new PluginResult(PluginResult.Status.NO_RESULT);
            faceBlink.setKeepCallback(true);
            callbackContext.sendPluginResult(faceBlink);
            return true;
          }
          return false;
      } catch (Exception e) {
          e.printStackTrace();
          PluginResult res = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
          callbackContext.sendPluginResult(res);
          return false;
      }
      
  }

  @Override
  public void onResume(boolean multitasking) {
      super.onResume(multitasking);
      if (Utils.blink)
      {
          Utils.blink = false;
          String outText = base64Transform(getBitmaps(Utils.pictureFinal));

          PluginResult faceBlink = new PluginResult(PluginResult.Status.OK,
                                                   outText);
          faceBlink.setKeepCallback(false);
          callbackContext.sendPluginResult(faceBlink);
      }
  }

  public Bitmap getBitmaps(byte[] data) {
      Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);
      return realImage;
  }

  public String base64Transform(Bitmap bitmap) {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
      byte[] byteArray = byteArrayOutputStream.toByteArray();
      String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
      return encoded;
  }
}