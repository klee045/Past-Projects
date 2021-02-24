# Done by Kai En
import random

lst_pplinQ = [int(x) for x in range(6, 16)]
[q1, q2, q3, q4, q5] = random.sample(lst_pplinQ, 5)
times = [int(x) for x in range(1, 6)]
[s1, s2, s3, s4, s5] = random.sample(times, 5)
[t1, t2, t3, t4, t5] = [q1 * s1, q2 * s2, q3 * s3, q4 * s4, q5 * s5]


def htmlString(day, stall, mealtime, q, t):
    if day == 'MWF' and mealtime == 'Breakfast':
        return '''<p align='center'>{stallName}</p><br>Mon/Wed/Fri {mealtime} Menu:
                  <p>
                  <p align='left'>1. {item1}<p align='right'>{price1}
                  <p align='left'>2. {item2}<p align='right'>{price2}
                  </p><br><br>
                  No. of People Queuing Now: {queue}<br>
                  Estimated Waiting Time: {waitTime} mins
                 '''.format(stallName=stall['stallName'],
                            mealtime=mealtime,
                            item1=stall['MonWedFri'][mealtime]['item_1']['itemName'],
                            price1=stall['MonWedFri'][mealtime]['item_1']['Price'],
                            item2=stall['MonWedFri'][mealtime]['item_2']['itemName'],
                            price2=stall['MonWedFri'][mealtime]['item_2']['Price'],
                            queue=q,
                            waitTime=t)

    elif day == 'MWF' and (mealtime == 'Lunch' or mealtime == 'Dinner'):
        return '''<p align='center'>{stallName}</p><br>Mon/Wed/Fri {mealtime} Menu:
                  <p>
                  <p align='left'>1. {item1}<p align='right'>{price1}
                  <p align='left'>2. {item2}<p align='right'>{price2}
                  <p align='left'>3. {item3}<p align='right'>{price3}
                  </p><br><br>
                  No. of People Queuing Now: {queue}<br>
                  Estimated Waiting Time: {waitTime} mins
                 '''.format(stallName=stall['stallName'],
                            mealtime=mealtime,
                            item1=stall['MonWedFri'][mealtime]['item_1']['itemName'],
                            price1=stall['MonWedFri'][mealtime]['item_1']['Price'],
                            item2=stall['MonWedFri'][mealtime]['item_2']['itemName'],
                            price2=stall['MonWedFri'][mealtime]['item_2']['Price'],
                            item3=stall['MonWedFri'][mealtime]['item_3']['itemName'],
                            price3=stall['MonWedFri'][mealtime]['item_3']['Price'],
                            queue=q,
                            waitTime=t)

    elif day == 'TTS' and mealtime == 'Breakfast':
        return '''<p align='center'>{stallName}</p><br>Tues/Thurs/Sat {mealtime} Menu:
                  <p>
                  <p align='left'>1. {item1}<p align='right'>{price1}
                  <p align='left'>2. {item2}<p align='right'>{price2}
                  </p><br><br>
                  No. of People Queuing Now: {queue}<br>
                  Estimated Waiting Time: {waitTime} mins
               '''.format(stallName=stall['stallName'],
                          mealtime=mealtime,
                          item1=stall['TueThurSat'][mealtime]['item_1']['itemName'],
                          price1=stall['TueThurSat'][mealtime]['item_1']['Price'],
                          item2=stall['TueThurSat'][mealtime]['item_2']['itemName'],
                          price2=stall['TueThurSat'][mealtime]['item_2']['Price'],
                          queue=q,
                          waitTime=t)

    elif day == 'TTS' and (mealtime == 'Lunch' or mealtime == 'Dinner'):
        return '''<p align='center'>{stallName}</p><br>Tues/Thurs/Sat {mealtime} Menu:
                  <p>
                  <p align='left'>1. {item1}<p align='right'>{price1}
                  <p align='left'>2. {item2}<p align='right'>{price2}
                  <p align='left'>3. {item3}<p align='right'>{price3}
                  </p><br><br>
                  No. of People Queuing Now: {queue}<br>
                  Estimated Waiting Time: {waitTime} mins
               '''.format(stallName=stall['stallName'],
                          mealtime=mealtime,
                          item1=stall['TueThurSat'][mealtime]['item_1']['itemName'],
                          price1=stall['TueThurSat'][mealtime]['item_1']['Price'],
                          item2=stall['TueThurSat'][mealtime]['item_2']['itemName'],
                          price2=stall['TueThurSat'][mealtime]['item_2']['Price'],
                          item3=stall['TueThurSat'][mealtime]['item_3']['itemName'],
                          price3=stall['TueThurSat'][mealtime]['item_3']['Price'],
                          queue=q,
                          waitTime=t)


def calcText(stall_seln, ppl, wait):
    return '''Stall Selected: {stall}
People in Queue: {pplq}
Estimated Waiting Time: {esti} mins'''.format(stall=stall_seln, pplq=ppl,
                                              esti=wait * ppl)
