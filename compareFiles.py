serverSearchFolder=r"M:\Downloads\quality-commands-template-1.20.1\.gradle\loom-cache\minecraftMaven\net\minecraft\minecraft-common-e572a196b7\1.20.1\minecraft-commonsources"
clientSearchFolder=r"M:\Downloads\quality-commands-template-1.20.1\.gradle\loom-cache\minecraftMaven\net\minecraft\minecraft-clientOnly-e572a196b7\1.20.1\clientOnly"
folders=[serverSearchFolder,clientSearchFolder]






#This python script is designed to accelerate development of mods, or in your case
#(if you are a modrinth moderator) to search mods for viruses faster when they get flagged.
#You will need to set the variables on lines 1 and 2 to the raw file paths of the folders you want to search.
#This script CANNOT edit files. It also CANNOT access the internet.

























#foldera="M:\\Downloads\\rendererstuff-template-1.21.8"
#foldera="M:\\Downloads\\endflashbackport"
#output=""
import os
def list_all_files(start_path='.'):
    allFiles=[]
    for root, dirs, files in os.walk(start_path):
        for file in files:
            allFiles.append((os.path.join(root, file)[start_path.__len__():],file))
    return allFiles

def anyIn(check,a):
    for i in check:
        if i in a:
            return True
    return False

def stringStartsWith(check,value):
    return check[:value.__len__()]==value

def withLineNumbers(lines,highlight=[],show=None):
    if show==None:
        return '\n'.join([i[1]+" "+i[0] for i in zip(lines.split('\n'),[(str(i+1)+[' -->' if i in highlight else ''][0]+"        ")[:6] for i in range(lines.count('\n'))])])
    else:
        return '\n'.join([i[1]+" "+i[0] for i in zip(lines.split('\n'),[(str(i+1)+[' -->' if i in highlight else ''][0]+"        ")[:6] for i in range(lines.count('\n'))],[i in show for i in range(lines.count('\n'))]) if i[2]])
try:
    clientFiles={i[1]:i[0] for i in list_all_files(folders[1])}
    if clientFiles=={}:
        print('Error! Client Search Folder is empty. Please modify line 2 to be an accurate full path!')
        __name__='nope'
except:
    print('Error! Client Search Folder is invalid. Please modify line 2 to be an accurate full path!')
    __name__='nope'
try:
    commonFiles={i[1]:i[0] for i in list_all_files(folders[0])}
    if commonFiles=={}:
        print('Error! Common Search Folder is empty. Please modify line 1 to be an accurate full path!')
        __name__='nope'
except:
    print('Error! Common Search Folder is invalid. Please modify line 1 to be an accurate full path!')
    __name__='nope'
cfilesList=[i for i in clientFiles]
sfilesList=[i for i in commonFiles]
print('Loaded',cfilesList.__len__(),"client files!")
print('Loaded',sfilesList.__len__(),"common files!")


if __name__=="__main__":
    print('If you want a list of commands, type "help"')
    while 1:
        print('Give command')
        cmd=input('>> ')
        if(stringStartsWith(cmd,'file ')):
            x=cmd[5:]
            if(x in cfilesList):
                print("Client: Found",x,"at",clientFiles[x])
            elif(x in sfilesList):
                print("Server: Found",x,"at",commonFiles[x])
            else:
                print("Not found, maybe try:")
                for i in cfilesList:
                    if(x.lower() in i.lower()):
                        print("|-Client:",i)
                print("Or:")
                for i in sfilesList:
                    if(x.lower() in i.lower()):
                        print("|-Server:",i)
        elif(stringStartsWith(cmd,'view ')):
            x=cmd[5:]
            if(x in cfilesList):
                contents=open(folders[1]+clientFiles[x],'r').read()
                print(x,"\n"+withLineNumbers(contents))
            elif(x in sfilesList):
                contents=open(folders[0]+commonFiles[x],'r').read()
                print(x,"\n"+withLineNumbers(contents))
            else:
                print("Not found, maybe try:")
                for i in cfilesList:
                    if(x.lower() in i.lower()):
                        print("|-Client:",i)
                print("Or:")
                for i in sfilesList:
                    if(x.lower() in i.lower()):
                        print("|-Server:",i)
        elif(stringStartsWith(cmd,'viewf ')):
            x=cmd[6:]
            x=x.split(' ')
            y=x[1]
            x=x[0]
            if(x in cfilesList):
                contents=open(folders[1]+clientFiles[x],'r').read()
                scontents=contents.split('\n')
                lines=[i for i in range(scontents.__len__()) if y in scontents[i]]
                linesb=lines+[i+1 for i in lines]+[i-1 for i in lines]+[i+2 for i in lines]+[i-2 for i in lines]
                lines=lines+[i+1 for i in lines]+[i-1 for i in lines]
                
                if lines==[]:
                    print('Not Found In File. Try something else.')
                else:
                    print(x,"\n"+withLineNumbers(contents,lines,linesb))
            elif(x in sfilesList):
                contents=open(folders[0]+commonFiles[x],'r').read()
                scontents=contents.split('\n')
                lines=[i for i in range(scontents.__len__()) if y in scontents[i]]
                linesb=lines+[i+1 for i in lines]+[i-1 for i in lines]+[i+2 for i in lines]+[i-2 for i in lines]
                lines=lines+[i+1 for i in lines]+[i-1 for i in lines]
                if lines==[]:
                    print('Not Found In File. Try something else.')
                else:
                    print(x,"\n"+withLineNumbers(contents,lines,linesb))
            else:
                print("Not found, maybe try:")
                for i in cfilesList:
                    if(x.lower() in i.lower()):
                        print("|-Client:",i)
                print("Or:")
                for i in sfilesList:
                    if(x.lower() in i.lower()):
                        print("|-Server:",i)
        elif cmd=='list':
            for i in cfilesList:
                print(clientFiles[i][1:].replace('.java','').replace('/','.').replace('\\','.'))
            for i in sfilesList:
                print(commonFiles[i][1:].replace('.java','').replace('/','.').replace('\\','.'))
            print('All files have been listed!')
        elif(stringStartsWith(cmd,'list ')):
            x=cmd[5:]
            
            for i in cfilesList:
                s=clientFiles[i][1:].replace('.java','').replace('/','.').replace('\\','.')
                
                if x in s:
                    print(s)
            for i in sfilesList:
                s=commonFiles[i][1:].replace('.java','').replace('/','.').replace('\\','.')
                if x in s:
                    print(s)
        elif(stringStartsWith(cmd,'ref ')):
            x=cmd[4:]
            found=[]
            count=clientFiles.__len__()+commonFiles.__len__()
            amountDone=0
            lastPercentage=0
            for i in clientFiles:
                try:
                    if(anyIn([j.lower() for j in x.split(' ')], open(clientSearchFolder+clientFiles[i],'r').read().lower())):
                        found.append(clientFiles[i])
                finally:
                    pass
                amountDone+=1
                n=int(amountDone/count*100)
                if n!=lastPercentage:
                    lastPercentage=n
                    print(f'[{n}%] complete')
            for i in commonFiles:
                try:
                    if(anyIn([j.lower() for j in x.split(' ')], open(serverSearchFolder+commonFiles[i],'r').read().lower())):
                        found.append(commonFiles[i])
                finally:
                    pass
                amountDone+=1
                n=int(amountDone/count*100)
                if n!=lastPercentage:
                    lastPercentage=n
                    print(f'[{n}%] complete')
            for i in found:
                s=i[1:].replace('.java','').replace('/','.').replace('\\','.')
                print(s)
        elif cmd=='help':
            print('Commands: ')
            print('file <fname> - search for files with name <fname>. If no exact matches, lists files with names containing keyword. Else, gives path to file. ".java" at the end is needed for a file path search.')
            print('view <fname> - print the contents of file <fname> to output with line numbers.')
            print('viewf <fname> <ref> - print the contents of file <fname> to output with line numbers. Only shows lines around references to <ref>.')
            print('list - lists all accessible files')
            print('list <path> - lists all accessible files that have a class import path including <path>. For finding files in a group.')
            print('ref <reference>... - Searches all files for instances of each reference. References are space-seperated. Example: [ref mixin cookie] would find all files with references to mixin or cookie. Not case-sensitive.')
            print('help - Prints this message!')
            print('quit - closes it. If it gets stuck, press CTRL+C to forcestop.')
        elif cmd=='quit':
            print('Exiting. Mewo!')
            break;
        else:
            print('unknown command \''+cmd[:cmd.index(' ')]+'\'')
    """
    replaceList=[('improvedphysics','endflashbackport'),('improved-physics','end-flash-backport'),('ImprovedPhysics','EndFlashBackport'),('improved_physics','end_flash_backport')]
    a=[i[0] for i in replaceList]
    
    
    flista=list_all_files(foldera)
    flistb=[]
    for i in flista:
        print('proccessing',i)
        try:
            if(anyIn(['blockcollisionspliterator'], open(foldera+i,'r').read().lower())):
                print(i)
                flistb.append(i)
            #if(anyIn(a, open(foldera+i,'r').read())):
            #    print(i)
            #    flistb.append(i)
            #    t=open(foldera+i,'r').read()
            #    for r in replaceList:
            #        t=t.replace(r[0],r[1])
            #    open(foldera+i,'w').write(t).close()
            #if('DrawContext' in i):
            #    print(i)
            #    flistb.append(i)
        except:
            print(i,"is bytecode")
    print("\n\n")
    for i in flistb:
        print(i)"""
#flistb=list_all_files(folderb)
#extrafiles=[i for i in flista if i not in flistb]

#print(extrafiles)

#print('\n\n',[i for i in flistb if i not in flista])