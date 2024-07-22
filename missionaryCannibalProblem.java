
public class mcProblem{
    
   static int im = 3, ic = 3, i, j, fm = 0, fc = 0, status = 0, flag = 0, slc = 0;

    static void display(char bpass1, char bpass2)
    {
        System.out.print( "\n\n\n");
        for (int i = 0; i < fm; i++)
        {
            System.out.print(" M ");
        }
        for (int i = 0; i < fc; i++)
        {
            System.out.print(" C ");
        }
        if (flag == 0)
        System.out.print( "     __________WATER___________B0(" + bpass1 + "," + bpass2 + ")AT  ");
        else
        System.out.print( "     BO(" + bpass1 + "," + bpass2 + ")AT__________WATER___________  ");
        for (int i = 0; i < im; i++)
        {
            System.out.print(" M ");
        }
        for (int i = 0; i < ic; i++)
        {
            System.out.print(" C ");
        }
    }

    static boolean win()
    {
        if (fc == 3 && fm == 3)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    static void solution()
    {
        while (win())
        {
            if (flag == 0)
            {
                switch (slc)
                {
                    case 1:display('C', ' ');
                        ic++;
                        break;
                    case 2:display('C', 'M');
                        ic++;
                        im++;
                        break;
                }
                if (((im - 2) >= ic && (fm + 2) >= fc) || (im - 2) == 0)
                {
                    im = im - 2;
                    slc = 1;
                    display('M', 'M');
                    flag = 1;
                }
                else if ((ic - 2) < im && (fm == 0 || (fc + 2) <= fm) || im == 0)
                {
                    ic = ic - 2;
                    slc = 2;
                    display('C', 'C');
                    flag = 1;
                }
                else if ((ic--) <= (im--) && (fm++) >= (fc++))
                {
                    ic = ic - 1;
                    im = im - 1;
                    slc = 3;
                    display('M', 'C');
                    flag = 1;
                }
            }
            else
            {
                switch (slc)
                {
                case 1:display('M', 'M');
                    fm = fm + 2;
                    break;
                case 2:display('C', 'C');
                    fc = fc + 2;
                    break;
                case 3:display('M', 'C');
                    fc = fc + 1;
                    fm = fm + 1;
                    break;
                }
                if (win())
                {
                    if (((fc > 1 && fm == 0) || im == 0))
                    {
                        fc--;
                        slc = 1;
                        display('C', ' ');
                        flag = 0;
                    }
                    else if ((ic + 2) > im)
                    {
                        fc--;
                        fm--;
                        slc = 2;
                        display('C', 'M');
                        flag = 0;
                    }
                }
            }
        }
    }

    public static void main(String args[])
    {
        System.out.println("MISSIONARIES AND CANNIBAL");
        display(' ',' ');
        solution();
        display(' ',' ');
        System.out.println();

    }
}

