import sys
from PyQt5.QtWidgets import (QApplication, QMainWindow, QDesktopWidget, QPushButton,
                             QMessageBox, QComboBox, QGridLayout, QLabel, QWidget)
from PyQt5.QtGui import QFont, QIcon
from PyQt5.QtCore import Qt
from InitialUI import masterUI
from FoodInfo import chicRice, roastDel, miniWok, western, soupDel
from StringFns import *


class MainWindow(QMainWindow):
    def __init__(self):     # Kai En & Andrew
        super().__init__()

        self.firstView = masterUI()     # initialize masterUI in main window

        self.btnPresses()   # connect all the buttons
        self.setCentralWidget(self.firstView)   # displays the layouts and fns
        self.setFixedSize(800, 590)
        self.setWindowIcon(QIcon('icon.jpg'))
        self.setWindowTitle('NTU Canteen')
        self.centerWindow()     # centers the window on screen
        self.show()     # displays main window

    def centerWindow(self):    # Kai En
        # creates a rectangle/frame w dimensions of main window
        # gets resolution of monitor and then center pt
        # set center of qr to center of screen
        # move our main window by shifting the top left pt of it to that of qr
        rect = self.frameGeometry()
        centerDesktop = QDesktopWidget().availableGeometry().center()
        rect.moveCenter(centerDesktop)
        self.move(rect.topLeft())

    def btnPresses(self):   # Kai En
        # Button Connections for Initial UI
        self.firstView.leftStack_stack0.btn_selectDate.clicked.connect(
            lambda: self.firstView.cal.show())      # calendar
        self.firstView.cal.btn_cfm.clicked.connect(
            self.firstView.cfm_date)        # cfm date selection
        self.firstView.leftStack_stack0.btn_operatingHrs.clicked.connect(
            self.operatingHrs)        # show operating hours
        self.firstView.rightStack.btn_clearAll.clicked.connect(
            self.clearAll)      # clear display screen

        # Button Connections for Select Store UI
        self.firstView.leftStack_stack1.btn_ChicRice.clicked.connect(
            lambda: self.firstView.setMenu(chicRice, q1, t1))    # Chic Rice
        self.firstView.leftStack_stack1.btn_RoastDel.clicked.connect(
            lambda: self.firstView.setMenu(roastDel, q2, t2))   # Roast Del
        self.firstView.leftStack_stack1.btn_MiniWok.clicked.connect(
            lambda: self.firstView.setMenu(miniWok, q3, t3))    # Mini Wok
        self.firstView.leftStack_stack1.btn_Western.clicked.connect(
            lambda: self.firstView.setMenu(western, q4, t4))    # Mini Wok
        self.firstView.leftStack_stack1.btn_SoupDel.clicked.connect(
            lambda: self.firstView.setMenu(soupDel, q5, t5))    # Soup Del
        self.firstView.leftStack_stack1.btn_return.clicked.connect(
            self.clearAll)   # Return

        self.firstView.rightStack.btn_waitTime.clicked.connect(  # Manual Input
            self.manualQ)

    def manualQ(self):  # Kai En
        # create a separate window to pop up to select no. of ppl & stall
        # upon clicking calculate, a message box with the calculated results
        # will pop up
        self.widget = QWidget()
        self.grid = QGridLayout()

        stalls = list(['Chicken Rice Stall', 'Roasted Delights Stall',
                       'Mini Wok Stall', 'Western Stall',
                       'Soup Delights Stall'])
        self.combo_pplSelect = QComboBox(self)
        self.combo_pplSelect.addItems(
            [str(x) for x in range(1, 101)])  # select no. of ppl in q
        self.combo_stallSelect = QComboBox(self)
        self.combo_stallSelect.addItems(stalls)     # select which stall
        self.msg_queue = QMessageBox(self)  # shows calculated results
        self.msg_queue.setWindowTitle('Estimate Waiting Time')
        self.btn_calculate = QPushButton('Calculate', self)
        self.btn_calculate.setFixedSize(150, 50)

        def getStallText():  # gets the selected stall from combo box
            return self.combo_stallSelect.currentText()

        def getPplSelText():  # gets the selected no. of ppl from combo box
            return int(self.combo_pplSelect.currentText())

        self.btn_calculate.clicked.connect(
            lambda: self.calculate(getStallText(), stalls, getPplSelText()))

        self.lbl_ppl = QLabel('Select No. of People: ', self)
        self.lbl_stall = QLabel('Select Stall: ', self)

        # Setting the layout of labels, buttons, etc
        self.grid.addWidget(self.lbl_stall, 0, 0, Qt.AlignRight)
        self.grid.addWidget(self.combo_stallSelect, 0, 1)
        self.grid.addWidget(self.lbl_ppl, 1, 0)
        self.grid.addWidget(self.combo_pplSelect, 1, 1)
        self.grid.addWidget(self.btn_calculate, 2, 0, 1, 2, Qt.AlignCenter)

        # Setting the layout of pop-up window upon clicking manual btn
        self.widget.setLayout(self.grid)
        self.widget.setWindowTitle('Manual Input People in Queue')
        self.widget.setFixedSize(450,150)
        self.widget.show()

    def calculate(self, stallSel, stalls, pplSelect_int):   # Kai En
        # to be used for manualQ
        # pop up window to show user input of ppl in queue
        # and estimated waiting time
        # after clicking calc btn, window closes to pop up calculated results
        # uses calcText from StringFns.py
        if stallSel == stalls[0]:
            self.msg_queue.setText(
                calcText(stall_seln=stallSel, ppl=pplSelect_int, wait=s1))
            self.msg_queue.show()
            self.widget.hide()
        elif stallSel == stalls[1]:
            self.msg_queue.setText(
                calcText(stall_seln=stalls[1], ppl=pplSelect_int, wait=s2))
            self.msg_queue.show()
            self.widget.hide()
        elif stallSel == stalls[2]:
            self.msg_queue.setText(
                calcText(stall_seln=stalls[2], ppl=pplSelect_int, wait=s3))
            self.msg_queue.show()
            self.widget.hide()
        elif stallSel == stalls[3]:
            self.msg_queue.setText(
                calcText(stall_seln=stalls[3], ppl=pplSelect_int, wait=s3))
            self.msg_queue.show()
            self.widget.hide()
        elif stallSel == stalls[4]:
            self.msg_queue.setText(
                calcText(stall_seln=stalls[4], ppl=pplSelect_int, wait=s4))
            self.msg_queue.show()
            self.widget.hide()

    def clearAll(self):   # Kai En
        # removes text on display screen & label below it
        # brings back to initial button UI
        self.firstView.rightStack.lbl_selDate.setText('')
        self.firstView.rightStack.textEdit.setText('')
        self.firstView.leftStack.setCurrentIndex(0)

    def operatingHrs(self):  # Andrew
        # store operating hours in a text file
        # open and read the file to display on message box
        f = open("Operating Hours.txt", "r")
        contents = f.read()
        opHrs = contents

        self.msg_opHrs = QMessageBox(self)
        self.msg_opHrs.setIcon(QMessageBox.Information)
        self.msg_opHrs.setWindowTitle('Operating Hours')
        self.msg_opHrs.setText(opHrs)
        self.msg_opHrs.setObjectName('operatingHrs')
        self.msg_opHrs.show()


# Kai En
if __name__ == '__main__':  # if ran directly as a script
    font = QFont('Helvetica')   # creates font
    app = QApplication(sys.argv)
    # set the background image and the background colours
    app.setStyleSheet('''QMainWindow {border-image: url(backgroundfood.jpeg);}
                         QLabel#SelectDate {background-color: #e6e6e6;}
                         QLabel#TimeNow {background-color: #e6e6e6;
                                         border-radius: 6px;}
                         QLabel#Head {background-color: #e6e6e6;
                                      border-radius: 6px;}
                         QTextEdit#TextEdit {background-color:#e6e6e6;
                                             border-radius: 6px;}
                         ''')
    app.setFont(font)   # set the font used for the entire program
    main = MainWindow()
    sys.exit(app.exec_())
