package net.spartanb312.base.utils.graphics.font;

import java.awt.Color;

public interface FontRenderer {
  int getFontHeight();
  
  int getStringHeight(String paramString);
  
  int getStringWidth(String paramString);
  
  void drawString(int paramInt1, int paramInt2, String paramString);
  
  void drawString(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString);
  
  void drawString(int paramInt1, int paramInt2, Color paramColor, String paramString);
  
  void drawString(int paramInt1, int paramInt2, int paramInt3, String paramString);
  
  void drawStringWithShadow(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String paramString);
}


/* Location:              C:\Users\XE\Desktop\moloch.su-b3.jar!\net\spartanb312\bas\\utils\graphics\font\FontRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */