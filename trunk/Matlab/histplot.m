function histplot( )

M = dlmread('matching.txt', '\n');
N = dlmread('not_matching.txt', '\n');
lM = length(M);
lN = length(N);

x = 0:1:100;
m = hist(M,x);
n = hist(N,x);

m = m./sum(m);
n = n./sum(n);

hold on
grid on
xlabel('Hamming distance')

plot(x,m,'b',x,n,'r');
legend('Match', 'Not a match');
hold off