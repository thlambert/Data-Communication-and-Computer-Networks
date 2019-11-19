
/**
 *
 * @author lambertth
 */
public class PolyAlphabet {
    private int adjust;
    private char[] alphabet, alphabet2;
    
    public PolyAlphabet()
    {
        String temp = "abcdefghijklmnopqrstuvwxyz";
        String temp2 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        alphabet = temp.toCharArray();
        alphabet2 = temp2.toCharArray();
    }
    //Receives a message and returns the encrypted message
    public String encrypt(String message)
    {
        String end = "";
        int flag = 0;
        int flag1 = 1;
        for(int x = 0; x < message.length(); x++)
        {
            int temp2 = -1;
            for(int y = 0; y < 26; y++)
            {
                if(message.charAt(x) == alphabet[y])
                {
                    temp2 = y;
                    flag1 = 1;
                }
                if(message.charAt(x) == alphabet2[y])
                {
                    temp2 = y;
                    flag1 = 2;
                }
            }
            if(temp2 != -1)
            {
                // 1 2 2 1 2
                switch (flag)
                {
                    case 0:
                        adjust = 5;
                        break;
                    case 1:
                        adjust = 19;
                        break;
                    case 2:
                        adjust = 19;
                        break;
                    case 3:
                        adjust = 5;
                        break;
                    case 4:
                        adjust = 19;
                        break;
                }
                flag = ++flag % 5;
                if(flag1 == 1)
                {
                   end += alphabet[(temp2 + adjust) % 26]; 
                }
                else
                {
                    end += alphabet2[(temp2 + adjust) % 26];
                }
                
            }
            else
            {
                end += message.charAt(x);
            }
        }
        return end;
    }
    //Receives an encrypted message and returns the unencrypted message
    public String decrypt(String message)
    {
        String end = "";
        int flag = 0;
        int flag1 = 1;
        for(int x = 0; x < message.length(); x++)
        {
            int temp2 = -1;
            for(int y = 0; y < 26; y++)
            {
                if(message.charAt(x) == alphabet[y])
                {
                    temp2 = y;
                    flag1 = 1;
                }
                if(message.charAt(x) == alphabet2[y])
                {
                    temp2 = y;
                    flag1 = 2;
                }
            }
            if(temp2 != -1)
            {
                // 1 2 2 1 2
                switch (flag)
                {
                    case 0:
                        adjust = 5;
                        break;
                    case 1:
                        adjust = 19;
                        break;
                    case 2:
                        adjust = 19;
                        break;
                    case 3:
                        adjust = 5;
                        break;
                    case 4:
                        adjust = 19;
                        break;
                }
                flag = ++flag % 5;
                if(flag1 == 1)
                {
                   if((temp2 - adjust) < 0)
                   {
                       end += alphabet[26 + (temp2 - adjust)]; 
                   }
                   else
                   {
                       end += alphabet[(temp2 - adjust)]; 
                   }
                }
                else
                {
                    if((temp2 - adjust) < 0)
                   {
                       end += alphabet2[26 + (temp2 - adjust)]; 
                   }
                   else
                   {
                       end += alphabet2[(temp2 - adjust)]; 
                   }
                }
            }
            else
            {
                end += message.charAt(x);
            }
        }
        
        return end;
    }
    
    public static void main(String argsp[])
    {
        PolyAlphabet test1 = new PolyAlphabet();
        System.out.println(test1.decrypt(test1.encrypt("Nice to meet you!")));
        
    }
}
 