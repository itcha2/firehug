
public class ByteSearch {
    
    protected byte[] b = null;
    
    public ByteSearch(){
        
    }
    
    public ByteSearch(byte[] b){
        this.b = b;
    }
    
    public void set(byte[] b){
        this.b = b;
    }
    
    public byte[] get(){
        return b;
    }
    
    public void removePattern(byte[] pattern) {
        
        int y;
        
        if (b == null || !b.length > 0) {
            return;
        }
        
        biterate: for(int x = 0; x < b.length; x++){
            if (b[x] == pattern[0]){
                patterniterate: for (y = 0; y < pattern.length; y++){
                    if(b[x+y] != pattern[y]){
                        continue biterate;
                    }
                }
              for (int z = 0; z < b.length - (x + y); z++) {
                  b[x + z] = b[x + y + z];
              }
              for (int z = x + y; z < b.length; z++) {
                  b[z] = null;
              }
            }
        }
    }
    
    public void appendPattern(byte[] pattern){
        byte newb = new byte[b.length + pattern.length];
        int x;
        for(x = 0; x < b.length; x++){
            newb[x] = b[x];
        }
        for (; x < b.length + pattern.length){
            newb[x] = pattern[x - b.length];
        }
        b = newb;
    }
}
