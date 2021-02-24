from PyQt5.QtWidgets import (QPushButton, QLabel, QVBoxLayout,
                             QWidget, QTextEdit, QHBoxLayout)


class InitialButtons(QWidget):      # leftStack_stack0 (Kai En)
    def __init__(self):
        super().__init__()

        self.vbox = QVBoxLayout(self)

        self.btn_viewStores = QPushButton('View Today\'s Stores', self)
        self.btn_viewStores.setObjectName('ViewStores')
        self.btn_selectDate = QPushButton('View Stores by Date', self)
        self.btn_operatingHrs = QPushButton('View Operating Hours', self)
        self.btn_viewStores.setFixedSize(200, 75)
        self.btn_selectDate.setFixedSize(200, 75)
        self.btn_operatingHrs.setFixedSize(200, 75)

        self.vbox.addWidget(self.btn_viewStores)
        self.vbox.addWidget(self.btn_selectDate)
        self.vbox.addWidget(self.btn_operatingHrs)
        self.vbox.setContentsMargins(100, 100, 0, 100)

        self.setLayout(self.vbox)


class selStall(QWidget):    # leftStack_stack1 (Kai En)
    def __init__(self):
        super().__init__()

        self.vbox = QVBoxLayout(self)

        self.btn_ChicRice = QPushButton('Chicken Rice', self)
        self.btn_RoastDel = QPushButton('Roasted Delight', self)
        self.btn_MiniWok = QPushButton('Mini Wok', self)
        self.btn_Western = QPushButton('Western Food', self)
        self.btn_SoupDel = QPushButton('Soup Delights', self)
        self.btn_return = QPushButton('Return to Main', self)
        self.btn_ChicRice.setFixedSize(200, 50)
        self.btn_RoastDel.setFixedSize(200, 50)
        self.btn_MiniWok.setFixedSize(200, 50)
        self.btn_Western.setFixedSize(200, 50)
        self.btn_SoupDel.setFixedSize(200, 50)
        self.btn_return.setFixedSize(200, 50)

        self.vbox.addWidget(self.btn_ChicRice)
        self.vbox.addWidget(self.btn_RoastDel)
        self.vbox.addWidget(self.btn_MiniWok)
        self.vbox.addWidget(self.btn_Western)
        self.vbox.addWidget(self.btn_SoupDel)
        self.vbox.addWidget(self.btn_return)
        self.vbox.setContentsMargins(100, 70, 0, 70)

        self.setLayout(self.vbox)


class infoPanel(QWidget):      # RightStack (Kai En)
    def __init__(self):
        super().__init__()

        self.hbox = QHBoxLayout()
        self.vbox = QVBoxLayout()

        self.textEdit = QTextEdit(self)
        self.textEdit.setObjectName('TextEdit')
        self.textEdit.setReadOnly(True)
        self.textEdit.setFixedWidth(300)

        self.lbl_selDate = QLabel(self)
        self.lbl_selDate.setObjectName('SelectDate')
        self.lbl_selDate.setMinimumWidth(10)

        self.btn_clearAll = QPushButton('Clear All', self)
        self.btn_clearAll.setFixedWidth(100)
        self.btn_waitTime = QPushButton('Manual Input Q', self)
        self.btn_waitTime.setFixedWidth(170)

        self.hbox.addWidget(self.btn_clearAll)
        self.hbox.addWidget(self.btn_waitTime)

        self.vbox.addLayout(self.hbox)
        self.vbox.addWidget(self.textEdit)
        self.vbox.addWidget(self.lbl_selDate)
        self.vbox.setContentsMargins(0, 10, 150, 10)

        self.setLayout(self.vbox)
