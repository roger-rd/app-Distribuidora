package cl.rdrp.planilla_shopper.util;

public class Config {
    public static final double RENDIMIENTO_KM_POR_LITRO = 10.0;
    public static final int PRECIO_LITRO = 1200;
    public static final double COMISION_PORC = 0.145; // 14.5%

    public static final int VALOR_UNIT_SKU = 61;   // $ por SKU
    public static final double VALOR_UNIT_KM = 234.0; // $ por KM (KM es double)
    public static final int BONO_POR_KM = 100;

    public static int basePorSku(int skuQty) {
        if (skuQty <= 0) return 0;
        if (skuQty <= 10) return 2623;
        if (skuQty <= 30) return 3026;
        if (skuQty <= 50) return 4035;
        if (skuQty <= 70) return 5044;
        if (skuQty <= 90) return 7061;
        if (skuQty <= 125) return 8070;
        if (skuQty <= 150) return 9079;
        return 10088;
    }


    // Calcula el bono en pesos
    public static int calcularBonoKm(double km, String fechaIso) {
        if (km <= 0) return 0;
        if (fechaIso == null || fechaIso.trim().isEmpty()) return 0;

        try {
            java.text.SimpleDateFormat sdf =
                    new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            java.util.Date d = sdf.parse(fechaIso);
            java.util.Calendar cal = java.util.Calendar.getInstance();
            cal.setTime(d);

            int day = cal.get(java.util.Calendar.DAY_OF_WEEK);

            // Aplica SOLO domingo, lunes y martes
            boolean aplica =
                    day == java.util.Calendar.SUNDAY ||
                            day == java.util.Calendar.MONDAY ||
                            day == java.util.Calendar.TUESDAY;

            if (!aplica) return 0;

            // $100 por km recorrido
            return (int) Math.round(km * 100.0);

        } catch (Exception e) {
            return 0; // si falla el parseo, mejor no dar bono
        }
    }

}


