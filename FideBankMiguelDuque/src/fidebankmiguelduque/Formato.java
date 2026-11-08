
package fidebankmiguelduque;

public class Formato {

    public static String moneda(double valor) {
        long centavos = Math.round(Math.abs(valor) * 100);
        String entero = String.valueOf(centavos / 100);
        StringBuilder conComas = new StringBuilder();
        for (int i = 0; i < entero.length(); i++) {
            if (i > 0 && (entero.length() - i) % 3 == 0) {
                conComas.append(',');
            }
            conComas.append(entero.charAt(i));
        }
        String decimales = String.valueOf(centavos % 100);
        if (decimales.length() == 1) {
            decimales = "0" + decimales;
        }
        return (valor < 0 ? "-" : "") + "₡" + conComas + "." + decimales;
    }

    public static String montoConSigno(double valor) {
        return (valor >= 0 ? "+" : "-") + moneda(Math.abs(valor));
    }

    public static double leerMonto(String texto) throws MontoInvalidoException {
        try {
            double monto = Double.parseDouble(texto.trim().replace(",", "").replace("₡", ""));
            if (monto <= 0) {
                throw new MontoInvalidoException();
            }
            return Math.round(monto * 100) / 100.0;
        } catch (NumberFormatException ex) {
            throw new MontoInvalidoException();
        }
    }

    public static String formatearId(int id) {
        String texto = String.valueOf(id);
        while (texto.length() < 3) {
            texto = "0" + texto;
        }
        return texto;
    }
}
