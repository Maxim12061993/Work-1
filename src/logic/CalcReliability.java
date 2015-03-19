package logic;

/**
 *
 * @author Максим
 */
public class CalcReliability {
    
    private final int BETA = 3;
    
    private int numberInGroup = 0; //номер в группе
    private int dayBirthday = 0; //день рождения
    private int monthBirthday = 0; //месяц рождения
    private int yearBirthday = 0; //год рождения(а точнее последние 2 числа)
    
    private long n = (long) Math.pow(10, 5);
    private float t = 0;
    private float t2 = 0;
    private float lambdaFromNull = 0;
    private float lambdaFromT = 0;
    
    private float pFromT = 0; //вероятность ошибочного ????
    private float fFromT = 0;
    private float[] pvFromT1 = new float[16]; //вероятность неправильных ошибочных воздействий
    private float[] pvFromT2 = new float[16]; //вероятность неправильных ошибочных воздействий
    public CalcReliability(int numberInGroup, int dayBirthday, 
            int monthBirthday, int yearBirthday) {
        this.numberInGroup = numberInGroup;
        this.dayBirthday = dayBirthday;
        this.monthBirthday = monthBirthday;
        this.yearBirthday = yearBirthday;
        this.calcBeginValue();
        this.calcPvRomT();
        this.calcPvRomT2();
    }
    
    //Метод рассчёта начальных значений t*, лямбда(0), лямбда(t*)
    private void calcBeginValue() {
        t = (float) numberInGroup / dayBirthday;
        t2 = (float) numberInGroup / (dayBirthday + monthBirthday);
        lambdaFromNull = (float) dayBirthday / (monthBirthday + yearBirthday);
        lambdaFromT = (float) BETA * lambdaFromNull * (float) Math.pow(t, BETA - 1);
        fFromT = (float) (1 - Math.exp((-1) * lambdaFromT * Math.pow(numberInGroup, 3)));
        pFromT = (float) 1 - fFromT;      
    } 
    
    //метод рассчёта вероятности неправильных ошибочных воздействий
    private void calcPvRomT() {
        for (int i = 0; i < 11; i++) {            
            float par1 = (float) Math.log1p(((lambdaFromT * t * n) / factorial(i + 1)) - 1);
            float par2 = (float) (-1) * lambdaFromT * t * n;
            pvFromT1[i] = par1 + par2;
        }
    }
    
    private void calcPvRomT2() {
        for (int i = 0; i < 11; i++) {            
            float par1 = (float) Math.log1p(((lambdaFromT * t2 * n) / factorial(i + 1)) - 1);
            float par2 = (float) (-1) * lambdaFromT * t2 * n;
            pvFromT2[i] = par1 + par2;
        }
    }
    
    //Метод рассчёта факториала
    private long factorial(int n) {
        if (n <= 1) {     
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    //Геттеры
    public int getNumberInGroup() {
        return numberInGroup;
    }

    public int getDayBirthday() {
        return dayBirthday;
    }

    public int getMonthBirthday() {
        return monthBirthday;
    }

    public int getYearBirthday() {
        return yearBirthday;
    }
    
    //Геттеры полученных результатов (без комментариев)
    public float getT() {
        return t;
    }
    
    public float getT2() {
        return t2;
    }
    
    public float getLambdaFromNull() {
        return lambdaFromNull;
    }
    
    public float getLambdaFromT() {
        return lambdaFromT;
    }
    
    public float getPFromT() {
        return pFromT;
    }
    
    public float getFFromT() {
        return fFromT;
    }
    
    public float getPvFromT1(int index) {
        return pvFromT1[index];
    }
    
    public float getPvFromT2(int index) {
        return pvFromT2[index];
    }
}
