import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class GetSystemDate {
    fun getCurrentDate(): String {
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}