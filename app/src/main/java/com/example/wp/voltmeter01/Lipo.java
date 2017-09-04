package com.example.wp.voltmeter01;

import android.util.Log;

/**
 * Created by wp on 04.09.2017.
 */

public class Lipo {
    /* Spannung je Zelle */
    private float cellU[] = new float [4];

    /* Gesamtspannung */
    private float uGes = 0f;

    private int nrCellsDetected = 0;

    private float s1FillLevel = 0f;
    private float s2FillLevel = 0f;
    private float s3FillLevel = 0f;
    private float s4FillLevel = 0f;
    private float fillLevel = 0f;


    public void updateData (float balancer1, float balancer2, float balancer3, float balancer4) {
        cellU[0] = balancer1;
        cellU[1] = balancer2 - balancer1;
        cellU[2] = balancer3 - balancer2;
        cellU[3] = balancer4 - balancer3;
        uGes = 0;

        Log.d ("updateData", "UGes = " + uGes);
        nrCellsDetected=0;
        if (cellU[0] > 0.5) {
            s1FillLevel = calcFillLevel(cellU[0]);
            uGes = cellU[0];
            nrCellsDetected++; // Annahme: Wenn mehr als 0.5 V gemessen werden, dann liegt eine Zelle vor. Verhindert dass durch Spannungsrauschen eine Zelle zuviel detektiert wird
        }
        if (cellU[1] > 0.5) {
            s2FillLevel = calcFillLevel(cellU[1]);
            uGes = uGes + cellU[1];
            nrCellsDetected++;
        }
        if (cellU[2] > 0.5) {
            s3FillLevel = calcFillLevel(cellU[2]);
            uGes = uGes + cellU[2];
            nrCellsDetected++;
        }
        if (cellU[3] > 0.5) {
            s4FillLevel = calcFillLevel(cellU[3]);
            uGes = uGes + cellU[3];
            nrCellsDetected++;
        }


        fillLevel = ((s1FillLevel+s2FillLevel+s3FillLevel+s4FillLevel) / nrCellsDetected) *100; // Filllevel = Durchschnitt aller Zellen; Todo: eventuell minimum aller Zellen nehmen?
        Log.d ("Filllevel", "" + fillLevel);
    }

    private float calcFillLevel (float u) {
        float level = 0;
        level = (u-3.55f)/0.6f;
        if (level < 0) level = 0;
        Log.d ("Filllevel: ", "U = " + u + "; Level = " + fillLevel);
        return level;

         /* Variante 1
 4.2V – 100%
 4.1V – 87%
 4.0V – 75%
 3.9V – 55%
 3.8V – 30%
 3.5V – 0%
     */

    /* Variante 2
    4,2 V --- 100 %
4,1 V --- 90 %
4,0 V --- 80 %
3,9 V --- 60 %
3,8 V --- 40 %
3,7 V --- 20 %
3,6 V --- 0 %
     */

    /*
    Variante 3
    Um die Energiemenge anzunähern passt folgende proportionale Formel ganz gut:

3,55+0,6* X% (0<X<1)

100,00% 4,15 V
74,02% 3,99 V
65,15% 3,94 V
61,47% 3,92 V
55,36% 3,88 V
52,58% 3,87 V
43,45% 3,81 V
41,33% 3,80 V
36,45% 3,77 V
31,70% 3,74 V
29,01% 3,72 V
27,01% 3,71 V
24,07% 3,69 V
21,34% 3,68 V
18,15% 3,66 V
15,95% 3,65 V
9,32% 3,61 V
6,38% 3,59 V
0,00% 3,55 V
     */
    }

    public int getNrCellsDetected() {
        return nrCellsDetected;
    }

    public float getCellU(int cell) {
        if (cell <= nrCellsDetected-1) {
            float value = cellU [cell];
            return value;
        }
        else return -1;
    }




    public float getuGes() {

        return uGes;
    }

    public float getFillLevel() {

        return fillLevel;
    }
}
