function histplot(matchfile, notmatchfile, titleText )

M = dlmread(matchfile, '\n');
N = dlmread(notmatchfile, '\n');
lM = length(M);
lN = length(N);

x = 0:1:100;
m = hist(M,x);
n = hist(N,x);

m = m./sum(m);
n = n./sum(n);

hold on
grid on
ylabel('Density')
xlabel('Hamming distance')
title(strcat('Normalised histogram:',' ',titleText));


plot(x./100,m,'b',x./100,n,'r',[0.33 0.33], [0 0.25],'g--');
legend('Match', 'Not a match', 'Threshold');
hold off