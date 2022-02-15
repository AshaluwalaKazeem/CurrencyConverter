package com.kazeem.currencyconverter.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast

class Utils {
    companion object {
        const val baseUrl = "http://data.fixer.io/api/"
        const val accessKey = "0869a55d67051f97019ecdeda328a705"
        const val latestEndpointData = """
    {
  "success":true,
  "timestamp":1644882962,
  "base":"EUR",
  "date":"2022-02-15",
  "rates":{
    "AED":4.152979,
    "AFN":104.577108,
    "ALL":121.200154,
    "AMD":541.432617,
    "ANG":2.038511,
    "AOA":596.696746,
    "ARS":120.202536,
    "AUD":1.585282,
    "AWG":2.035446,
    "AZN":1.917454,
    "BAM":1.95584,
    "BBD":2.283812,
    "BDT":97.247718,
    "BGN":1.958416,
    "BHD":0.426231,
    "BIF":2278.251742,
    "BMD":1.130646,
    "BND":1.524508,
    "BOB":7.776441,
    "BRL":5.896775,
    "BSD":1.131106,
    "BTC":2.6545229e-5,
    "BTN":85.336255,
    "BWP":13.05387,
    "BYN":2.927838,
    "BYR":22160.662104,
    "BZD":2.280012,
    "CAD":1.439047,
    "CDF":2278.251519,
    "CHF":1.045356,
    "CLF":0.033329,
    "CLP":919.644779,
    "CNY":7.187967,
    "COP":4459.742797,
    "CRC":725.353866,
    "CUC":1.130646,
    "CUP":29.96212,
    "CVE":109.763083,
    "CZK":24.56837,
    "DJF":200.938657,
    "DKK":7.442363,
    "DOP":64.616285,
    "DZD":159.414315,
    "EGP":17.769798,
    "ERN":16.959712,
    "ETB":56.989379,
    "EUR":1,
    "FJD":2.426875,
    "FKP":0.82302,
    "GBP":0.835483,
    "GEL":3.391835,
    "GGP":0.82302,
    "GHS":7.292973,
    "GIP":0.823019,
    "GMD":60.035252,
    "GNF":10195.603903,
    "GTQ":8.698246,
    "GYD":236.651261,
    "HKD":8.822487,
    "HNL":27.8082,
    "HRK":7.529087,
    "HTG":114.937613,
    "HUF":357.176872,
    "IDR":16172.138897,
    "ILS":3.687975,
    "IMP":0.82302,
    "INR":85.573679,
    "IQD":1650.888798,
    "IRR":47769.79444,
    "ISK":141.997936,
    "JEP":0.82302,
    "JMD":177.658947,
    "JOD":0.801627,
    "JPY":130.62749,
    "KES":128.498121,
    "KGS":95.872113,
    "KHR":4611.524577,
    "KMF":488.495972,
    "KPW":1017.581601,
    "KRW":1353.949806,
    "KWD":0.342017,
    "KYD":0.942605,
    "KZT":487.802567,
    "LAK":12943.968984,
    "LBP":1713.547151,
    "LKR":229.051021,
    "LRD":173.665139,
    "LSL":17.208984,
    "LTL":3.338504,
    "LVL":0.683916,
    "LYD":5.178129,
    "MAD":10.615072,
    "MDL":20.190408,
    "MGA":4511.277431,
    "MKD":61.619416,
    "MMK":2011.181018,
    "MNT":3232.326094,
    "MOP":9.088448,
    "MRO":403.640437,
    "MUR":49.070461,
    "MVR":17.468195,
    "MWK":906.30683,
    "MXN":23.079541,
    "MYR":4.737967,
    "MZN":72.16951,
    "NAD":17.208323,
    "NGN":470.054896,
    "NIO":40.081345,
    "NOK":10.058792,
    "NPR":136.537728,
    "NZD":1.708583,
    "OMR":0.435302,
    "PAB":1.131106,
    "PEN":4.280599,
    "PGK":3.974234,
    "PHP":58.21134,
    "PKR":197.693741,
    "PLN":4.554947,
    "PYG":7859.541887,
    "QAR":4.11668,
    "RON":4.943634,
    "RSD":117.587197,
    "RUB":86.517602,
    "RWF":1152.1283,
    "SAR":4.242379,
    "SBD":9.136571,
    "SCR":15.125797,
    "SDG":501.436583,
    "SEK":10.612538,
    "SGD":1.52309,
    "SHP":1.557353,
    "SLL":13115.493507,
    "SOS":662.558374,
    "SRD":22.955545,
    "STD":23402.089967,
    "SVC":9.897053,
    "SYP":2840.182563,
    "SZL":17.196392,
    "THB":36.672504,
    "TJS":12.758913,
    "TMT":3.957261,
    "TND":3.254848,
    "TOP":2.564248,
    "TRY":15.372718,
    "TTD":7.677641,
    "TWD":31.516422,
    "TZS":2616.314991,
    "UAH":32.264216,
    "UGX":3977.021195,
    "USD":1.130646,
    "UYU":48.85826,
    "UZS":12267.509562,
    "VEF":241766291066.96063,
    "VND":25699.584165,
    "VUV":128.522132,
    "WST":2.953635,
    "XAF":655.960496,
    "XAG":0.05021,
    "XAU":0.000625,
    "XCD":3.055627,
    "XDR":0.806604,
    "XOF":650.121543,
    "XPF":119.707129,
    "YER":282.944117,
    "ZAR":17.087797,
    "ZMK":10177.169796,
    "ZMW":21.242313,
    "ZWL":364.067559
  }
}
"""




        /**
         * This method is used to get the connection of a device
         * @param context Application context
         * @return It returns an int
        0: No Internet available (maybe on airplane mode, or in the process of joining an wi-fi).

        1: Cellular (mobile data, 3G/4G/LTE whatever).

        2: Wi-fi.

        3: VPN
         */
        private fun getConnectionType(context: Context): Int {
            var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            result = 2
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            result = 1
                        }
                        hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> {
                            result = 3
                        }
                    }
                }
            }
            return result
        }

        internal fun isInternetConnected(context: Context): Boolean {
            return getConnectionType(context) != 0
        }

        /**
         * This method is used to copy text to clipboard
         * @param context The application
         * @param text The text that that you want to copy to clipboard
         * @param message The message that you want to display after the text has been copied to clipboard
         */
        fun copyTextToClipboard(context : Context, text : String, message: String){
            val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}