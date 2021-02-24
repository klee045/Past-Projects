from time import strftime
from datetime import date, datetime, time
from PyQt5.QtWidgets import (QWidget, QGridLayout, QLabel, QStackedWidget,
                             QMessageBox)
from PyQt5.QtCore import QTimer, Qt
from PyQt5.QtGui import QFont
from workalendar.asia import singapore
from Layouts import InitialButtons, selStall, infoPanel
from CalendarUI import *
from StringFns import *
from FoodInfo import *


class masterUI(QWidget):
    def __init__(self):  # Kai En and Andrew
        super().__init__()

        # Create the timer
        self.timer = QTimer()
        # when timer times out, update the label to display most current time
        self.timer.timeout.connect(lambda: self.lbl_timeNow.setText(
            strftime("Date: %d/%m/%y Time: %r")))
        self.timer.start(0)  # starts the timer with a timeout interval of 0
        self.lbl_timeNow = QLabel(self)
        self.lbl_timeNow.setObjectName('TimeNow')

        self.lbl_head = QLabel('NTU Menu', self)
        self.lbl_head.setObjectName('Head')

        self.cal = CalendarUI()  # create calendar object

        self.setFont_lbl()  # set font size to 25
        self.setMainLayout()  # set layout of the main window
        self.today_closed_or_open()  # checks if canteen is open or closed

    def setMainLayout(self):  # Kai En
        # Use a grid layout encompassing the entire layout
        # top of grid is the label head, bottom displays time
        # in the middle, left is a stacked widget used to switch between
        # the different button UIs
        # right side is another layout specified in Layouts.py
        self.outermostLayout = QGridLayout(self)

        self.leftStack = QStackedWidget(self)
        self.leftStack_stack0 = InitialButtons()
        self.leftStack_stack1 = selStall()
        self.leftStack.addWidget(self.leftStack_stack0)  # Main Page
        self.leftStack.addWidget(self.leftStack_stack1)  # Select Stall

        self.rightStack = infoPanel()

        # arranging layout of main window
        self.outermostLayout.addWidget(
            self.lbl_head, 0, 0, 1, 3, Qt.AlignCenter)
        self.outermostLayout.addWidget(self.leftStack, 1, 1)
        self.outermostLayout.addWidget(self.rightStack, 1, 2)
        self.outermostLayout.addWidget(
            self.lbl_timeNow, 3, 0, 1, 3, Qt.AlignCenter)
        self.outermostLayout.setColumnMinimumWidth(0, 10)

        self.setLayout(self.outermostLayout)

    def setFont_lbl(self):  # Andrew
        # sets the font size of 25 for the head and time labels
        font_lbl = QFont()
        font_lbl.setPointSize(25)
        self.lbl_head.setFont(font_lbl)
        self.lbl_timeNow.setFont(font_lbl)

    def today_closed_or_open(self):  # Kai En
        # checks if the canteen is open today
        today = date.today()  # return today's local date
        day_value = today.weekday()  # return day of week as a value
        now = datetime.now()  # return current local date and time
        timeNow = now.time()  # create a time object for comparison
        cal = singapore.Singapore()  # get SG's Public Holidays

        # Mon is 0, Sunday is 6
        if day_value is 6:  # if its Sun, closed
            self.leftStack_stack0.btn_viewStores.clicked.connect(
                self.closedMsg)
        # if not Sat & not a working day, closed
        elif day_value is not 5 and not cal.is_working_day(today):
            self.leftStack_stack0.btn_viewStores.clicked.connect(
                self.closedMsg)
        # if its Sat and time now is not btwn 7am-3pm
        elif day_value is 5 and not time(hour=7) <= timeNow <= time(hour=15):
            self.leftStack_stack0.btn_viewStores.clicked.connect(
                self.closedMsg)
        # if its Mon-Fri and time now is not btwn 7am-9pm
        elif 0 <= day_value <= 4 and not time(hour=7) <= timeNow <= time(hour=21):
            self.leftStack_stack0.btn_viewStores.clicked.connect(
                self.closedMsg)
        # if code reaches here, canteen is open
        else:
            self.leftStack_stack0.btn_viewStores.clicked.connect(
                lambda: self.leftStack.setCurrentIndex(1))

    def cal_today(self):  # Kai En
        # checks if store is closed
        # if selected date on cal is outside working hours
        today = date.today()    # return today's local date
        day_value = today.weekday()  # return today's day of week as int
        timeSel = self.cal.combo_box.currentText()  # return user's selected date
        timeSel_timeobj = time(int(timeSel[0:2]))  # convert selected time to time object

        if day_value == 6:
            self.closedMsg()
        elif day_value == 5:
            if time(hour=7) <= timeSel_timeobj < time(hour=15):
                self.leftStack.setCurrentIndex(1)
            else:
                self.closedMsg()
        elif day_value != 5:
            if time(hour=7) <= timeSel_timeobj < time(hour=21):
                self.leftStack.setCurrentIndex(1)
            else:
                self.closedMsg()

    def cfm_date(self):  # Andrew
        # check if store is closed
        # if selected date is a public holiday
        global logic
        selDate_datetime_date = self.cal.calendar.selectedDate().toPyDate() # retrieve date from CalendarUI
        selDate_val = selDate_datetime_date.weekday() # return day of week as int
        today = date.today()  # return today's local date
        selDate = self.cal.calendar.selectedDate() # date selected from calendar
        cbx = self.cal.combo_box.currentText()  # retrieve value from combo box
        cal = singapore.Singapore()  # get SG's Public Holidays

        # display selected date below listview
        self.rightStack.lbl_selDate.setText(selDate.toPyDate().strftime('%A, %d/%m/%Y') + " " + cbx)
        self.cal.hide() # hide calendar

        # checking for all holidays
        for var in cal.holidays():
            todaydate, day = var  # splitting value retrieved from holiday into todaydate and day
            logic = True
            if selDate == todaydate:
                self.closedMsgHol(day)
                logic = False
                break

        if selDate_val == 6:
            self.closedMsg()
        elif today == selDate_datetime_date:
            self.cal_today()
        elif logic == True:
            self.leftStack.setCurrentIndex(1)
            self.rightStack.textEdit.setText('`')

    def closedMsg(self):  # Kai En
        # pop up message box, saying canteen is closed
        self.msg_opHrs = QMessageBox(self)
        self.msg_opHrs.setIcon(QMessageBox.Warning)
        self.msg_opHrs.setWindowTitle('WARNING')
        self.msg_opHrs.setText('Canteen is closed.')
        self.msg_opHrs.show()

    def closedMsgHol(self, day):  # Andrew
        # pop up message box, saying canteen is closed on public holiday
        self.msg_opHrs = QMessageBox(self)
        self.msg_opHrs.setIcon(QMessageBox.Warning)
        self.msg_opHrs.setWindowTitle('WARNING')
        self.msg_opHrs.setText('Canteen is closed. It\'s ' + day + '!')
        self.msg_opHrs.show()

    def setMenu(self, stallName, q, t):  # Andrew & Kai En
        selDate_datetime_date = self.cal.calendar.selectedDate().toPyDate()
        selDate_val = selDate_datetime_date.weekday()
        today = date.today()
        day_value = today.weekday()
        currenttime = self.cal.combo_box.currentText()
        timeSel_timeobj = time(hour=int(currenttime[0:2]))

        # retrieving combo box text from calendar and changing the value for python to read (Andrew)
        if currenttime == "08:00:00":
            inttime = 800  # convert dropdown text into integer
        elif currenttime == "09:00:00":
            inttime = 900  # convert dropdown text into integer
        else:
            newtime = currenttime.replace(":", "")
            newtime2 = newtime[0:4]
            inttime = int(newtime2)

        if selDate_datetime_date != today:
            if selDate_val == 0 or selDate_val == 2 or selDate_val == 4:
                if 700 <= inttime < 1200:
                    self.rightStack.textEdit.setHtml(
                        htmlString('MWF', stallName, 'Breakfast', '-', '-'))
                elif 1200 <= inttime < 1800:
                    self.rightStack.textEdit.setHtml(
                        htmlString('MWF', stallName, 'Lunch', '-', '-'))
                elif 1800 <= inttime <= 2100:
                    self.rightStack.textEdit.setHtml(
                        htmlString('MWF', stallName, 'Dinner', '-', '-'))
                else:
                    self.closedMsg()

            elif selDate_val == 1 or selDate_val == 3 or selDate_val == 5:
                if 700 <= inttime < 1200:
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Breakfast', '-', '-'))
                elif 1200 <= inttime < 1800:
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Lunch', '-', '-'))
                elif 1800 <= inttime <= 2100:
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Dinner', '-', '-'))
                else:
                    self.closedMsg()

            elif day_value == 6:
                self.closedMsg()

        # for Today's Menu (Kai En)
        # if selected date is today, and it is Mon/Wed/Fri or Tues/Thur/Sat
        # depending on the time, it will display the appropriate menu
        # outside operating hours, canteen is closed
        # automatically closed when if today/selected date is Sun
        elif selDate_datetime_date == today:
            if day_value == 0 or day_value == 2 or day_value == 4:
                if time(hour=7) <= timeSel_timeobj < time(hour=12):
                    self.rightStack.textEdit.setHtml(
                        htmlString('MWF', stallName, 'Breakfast', q, t))
                elif time(hour=12) <= timeSel_timeobj < time(hour=18):
                    self.rightStack.textEdit.setHtml(
                        htmlString('MWF', stallName, 'Lunch', q, t))
                elif time(hour=18) <= timeSel_timeobj <= time(hour=21):
                    self.rightStack.textEdit.setHtml(
                        htmlString('MWF', stallName, 'Dinner', q, t))
                else:
                    self.closedMsg()

            elif day_value == 1 or day_value == 3:
                if time(hour=7) <= timeSel_timeobj < time(hour=12):
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Breakfast', q, t))
                elif time(hour=12) <= timeSel_timeobj < time(hour=18):
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Lunch', q, t))
                elif time(hour=18) <= timeSel_timeobj <= time(hour=21):
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Dinner', q, t))
                else:
                    self.closedMsg()

            elif day_value == 5:
                if time(hour=7) <= timeSel_timeobj < time(hour=12):
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Breakfast', q, t))
                elif time(hour=12) <= timeSel_timeobj <= time(hour=15):
                    self.rightStack.textEdit.setHtml(
                        htmlString('TTS', stallName, 'Lunch', q, t))
                else:
                    self.closedMsg()

            elif day_value == 6:
                self.closedMsg()
