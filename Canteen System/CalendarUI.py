from PyQt5.QtWidgets import (QCalendarWidget, QWidget, QLabel,
                             QGridLayout, QPushButton, QComboBox)
from PyQt5.QtCore import QDate, Qt
from datetime import date, datetime, time
from workalendar.asia import singapore
import calendar


class CalendarUI(QWidget):
    def __init__(self):
        super().__init__()
        # Andrew
        # creating a calendar
        currentYear = datetime.now().year
        currentMonth = datetime.now().month
        self.calendar = QCalendarWidget(self)
        self.calendar.setGridVisible(True)
        self.calendar.setMinimumDate(
            QDate(currentYear, currentMonth - 10, 1))  # set min range for month
        self.calendar.setMaximumDate(
            QDate(currentYear, currentMonth + 1,
                  calendar.monthrange(currentYear, currentMonth)[1]))  # set max range for month
        self.calendar.clicked[QDate].connect(
            self.show_date)  # retrieve value selected
        self.lbl_selectedDate = QLabel(self)
        selDate = self.calendar.selectedDate()
        self.lbl_selectedDate.setText(
            "Today's Date: " + selDate.toPyDate().strftime('%A, %d/%m/%Y'))
        self.lbl_cfmMsg = QLabel(self)
        self.lbl_cfmMsg.setText('Select Your Desired Date.')
        self.lbl_time = QLabel(self)
        self.lbl_time.setText('Select Your Desired Time:')

        # Fixed Attributes to be used in other functions
        selDate_datetime_date = self.calendar.selectedDate().toPyDate()
        selDate_val = selDate_datetime_date.weekday()
        today = date.today()
        day_value = today.weekday()
        timeNow = datetime.now().time()
        cal = singapore.Singapore()

        self.combo_box = QComboBox(self)
        self.combo_box.addItem("08:00:00")
        self.combo_box.addItem("09:00:00")
        self.combo_box.addItem("10:00:00")
        self.combo_box.addItem("11:00:00")
        self.combo_box.addItem("12:00:00")
        self.combo_box.addItem("13:00:00")
        self.combo_box.addItem("14:00:00")
        self.combo_box.addItem("15:00:00")
        self.combo_box.addItem("16:00:00")
        self.combo_box.addItem("17:00:00")
        self.combo_box.addItem("18:00:00")
        self.combo_box.addItem("19:00:00")
        self.combo_box.addItem("20:00:00")
        self.combo_box.addItem("21:00:00")

        # Kai En
        # set default combo box value to the current hour of the day
        # if outside operating hours, set default to 8am
        tim = []
        for num in range(8, 22):
            tim.append(time(hour=num))
        tim1 = [str(x) for x in tim]

        if time(hour=8) <= timeNow <= time(hour=21):
            self.combo_box.setCurrentIndex(
                tim1.index(str(timeNow)[0:2] + ':00:00'))
        else:
            self.combo_box.setCurrentIndex(0)
        self.btn_cfm = QPushButton('Confirm Selection', self)

        # Kai En
        self.grid = QGridLayout()
        self.grid.addWidget(self.calendar, 0, 0, 1, 2)
        self.grid.addWidget(self.lbl_selectedDate, 2, 0, 1, 2)
        self.grid.addWidget(self.lbl_cfmMsg, 1, 0, 1, 2)
        self.grid.addWidget(self.lbl_time, 3, 0)
        self.grid.addWidget(self.combo_box, 3, 1, Qt.AlignLeft)
        self.grid.addWidget(self.btn_cfm, 4, 0, 1, 2)
        self.grid.setColumnMinimumWidth(0, 10)

        self.setLayout(self.grid)
        self.setWindowTitle('Calendar')

    def show_date(self, selDate):  # method to display date onto calendar
        self.lbl_selectedDate.setText(
            'Selected Date: ' + selDate.toPyDate().strftime('%A, %d/%m/%Y'))
