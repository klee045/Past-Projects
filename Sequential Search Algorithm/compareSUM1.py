# searching for pattern in text (USING ASCII VALUE AND COMPARE SUM)

# FIND the sum of pattern (patternSum)

# PLACE a firstIndex on the first letter (index 0) of the text
# PLACE a lastIndex on the lengthOfPattern-1 (index) of the text

# FIND the sum of the firstIndex to lastIndex of text (textSum)

# IF patternSum == textSum

# IF firstIndex of text == first index of pattern (SIMILAR TO NAIVE/BRUTEFORCE)
# keep checking till there is a mismatch/reach END of pattern

# IF there is a mismatch
# UPDATE firstIndex,lastIndex position
# SUBTRACT previous firstIndex, ADD next lastIndex
# LOOP back to patternSum == textSum

# ELSE IF reach END of pattern (means all match), Return firstIndex value
# UPDATE firstIndex,lastIndex position
# SUBTRACT previous firstIndex, ADD next lastIndex
# LOOP back to patternSum == textSum

# ELSE IF patternSum != textSum
# UPDATE firstIndex,lastIndex position
# SUBTRACT previous firstIndex, ADD next lastIndex
# LOOP back to patternSum == textSum

##################################################################################


def compareSUM1(text, pattern):
    patternSum = 0
    textSum = 0
    k = 0
    firstIndex = 0
    z = firstIndex
    lastIndex = len(pattern) - 1
    index = []
    # FIND the sum of pattern = patternSum
    patternSum = sum(map(ord, pattern))

    # FIND the sum of the firstIndex to lastIndex of text = textSum
    textSum = sum(map(ord, text[firstIndex: lastIndex + 1]))

    # LOOP through whole text
    while lastIndex < len(text):
        # PREVENT OUT OF STRING
        if z > len(text) - 1:
            break
        # IF patternSUM == textSUM
        if patternSum == textSum:
            for k in range(len(pattern)):
                # CHECK if pattern == text
                if text[z] == pattern[k]:
                    z += 1
                    # CHECK if WHOLE pattern == text
                    if k == len(pattern) - 1:
                        index += [firstIndex]
                        break
                else:
                    # UPDATE textSum, firstIndex, lastIndex
                    textSum = textSum - ord(text[firstIndex])
                    firstIndex += 1
                    z = firstIndex
                    lastIndex += 1
                    # PREVENT OUT OF STRING
                    if lastIndex > len(text) - 1:
                        break
                    textSum = textSum + ord(text[lastIndex])
                    break
            continue

        # IF patternSUM != textSUM
        else:
            # UPDATE textSum, firstIndex, lastIndex
            textSum = textSum - ord(text[firstIndex])
            firstIndex += 1
            z = firstIndex
            lastIndex += 1
            # PREVENT OUT OF STRING
            if lastIndex > len(text) - 1:
                break
            textSum = textSum + ord(text[lastIndex])
    return index
